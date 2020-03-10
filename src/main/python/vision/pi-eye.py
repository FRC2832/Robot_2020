import numpy as np
import cv2
import os, math
from networktables import NetworkTables
import threading
from cscore import CameraServer

cond = threading.Condition()
notified = [False]

from enum import Enum


class ImgProc:
    """
    An OpenCV pipeline generated by GRIP.
    """
    def __init__(self):
        """initializes all values to presets or None if need to be set
        """

        self.__hsv_threshold_hue = [51.798561151079134, 180.0]
        self.__hsv_threshold_saturation = [
            34.39748201438849, 132.65151515151513
        ]
        self.__hsv_threshold_value = [233.90287769784172, 255.0]

        self.hsv_threshold_output = None

        self.__blur_input = self.hsv_threshold_output
        self.__blur_type = BlurType.Box_Blur
        self.__blur_radius = 6.396396396396398

        self.blur_output = None

        self.__find_contours_input = self.blur_output
        self.__find_contours_external_only = False

        self.find_contours_output = None

        self.__filter_contours_contours = self.find_contours_output
        self.__filter_contours_min_area = 500.0
        self.__filter_contours_min_perimeter = 510.0
        self.__filter_contours_min_width = 0.0
        self.__filter_contours_max_width = 1000.0
        self.__filter_contours_min_height = 0.0
        self.__filter_contours_max_height = 1000.0
        self.__filter_contours_solidity = [0, 100.0]
        self.__filter_contours_max_vertices = 1000000.0
        self.__filter_contours_min_vertices = 0.0
        self.__filter_contours_min_ratio = 0.0
        self.__filter_contours_max_ratio = 1000.0

        self.filter_contours_output = None

    def process(self, source0):
        """
        Runs the pipeline and sets all outputs to new values.
        """
        # Step HSV_Threshold0:
        self.__hsv_threshold_input = source0
        (self.hsv_threshold_output) = self.__hsv_threshold(
            self.__hsv_threshold_input, self.__hsv_threshold_hue,
            self.__hsv_threshold_saturation, self.__hsv_threshold_value)

        # Step Blur0:
        self.__blur_input = self.hsv_threshold_output
        (self.blur_output) = self.__blur(self.__blur_input, self.__blur_type,
                                         self.__blur_radius)

        # Step Find_Contours0:
        self.__find_contours_input = self.blur_output
        (self.find_contours_output) = self.__find_contours(
            self.__find_contours_input, self.__find_contours_external_only)

        # Step Filter_Contours0:
        self.__filter_contours_contours = self.find_contours_output
        (self.filter_contours_output) = self.__filter_contours(
            self.__filter_contours_contours, self.__filter_contours_min_area,
            self.__filter_contours_min_perimeter,
            self.__filter_contours_min_width, self.__filter_contours_max_width,
            self.__filter_contours_min_height,
            self.__filter_contours_max_height, self.__filter_contours_solidity,
            self.__filter_contours_max_vertices,
            self.__filter_contours_min_vertices,
            self.__filter_contours_min_ratio, self.__filter_contours_max_ratio)

    @staticmethod
    def __hsv_threshold(input, hue, sat, val):
        """Segment an image based on hue, saturation, and value ranges.
        Args:
            input: A BGR numpy.ndarray.
            hue: A list of two numbers the are the min and max hue.
            sat: A list of two numbers the are the min and max saturation.
            lum: A list of two numbers the are the min and max value.
        Returns:
            A black and white numpy.ndarray.
        """
        out = cv2.cvtColor(input, cv2.COLOR_BGR2HSV)
        return cv2.inRange(out, (hue[0], sat[0], val[0]),
                           (hue[1], sat[1], val[1]))

    @staticmethod
    def __blur(src, type, radius):
        """Softens an image using one of several filters.
        Args:
            src: The source mat (numpy.ndarray).
            type: The blurType to perform represented as an int.
            radius: The radius for the blur as a float.
        Returns:
            A numpy.ndarray that has been blurred.
        """
        if type is BlurType.Box_Blur:
            ksize = int(2 * round(radius) + 1)
            return cv2.blur(src, (ksize, ksize))
        elif type is BlurType.Gaussian_Blur:
            ksize = int(6 * round(radius) + 1)
            return cv2.GaussianBlur(src, (ksize, ksize), round(radius))
        elif type is BlurType.Median_Filter:
            ksize = int(2 * round(radius) + 1)
            return cv2.medianBlur(src, ksize)
        else:
            return cv2.bilateralFilter(src, -1, round(radius), round(radius))

    @staticmethod
    def __find_contours(input, external_only):
        """Sets the values of pixels in a binary image to their distance to the nearest black pixel.
        Args:
            input: A numpy.ndarray.
            external_only: A boolean. If true only external contours are found.
        Return:
            A list of numpy.ndarray where each one represents a contour.
        """
        if external_only:
            mode = cv2.RETR_EXTERNAL
        else:
            mode = cv2.RETR_LIST
        method = cv2.CHAIN_APPROX_SIMPLE
        im2, contours, hierarchy = cv2.findContours(input,
                                                    mode=mode,
                                                    method=method)
        return contours

    @staticmethod
    def __filter_contours(input_contours, min_area, min_perimeter, min_width,
                          max_width, min_height, max_height, solidity,
                          max_vertex_count, min_vertex_count, min_ratio,
                          max_ratio):
        """Filters out contours that do not meet certain criteria.
        Args:
            input_contours: Contours as a list of numpy.ndarray.
            min_area: The minimum area of a contour that will be kept.
            min_perimeter: The minimum perimeter of a contour that will be kept.
            min_width: Minimum width of a contour.
            max_width: MaxWidth maximum width.
            min_height: Minimum height.
            max_height: Maximimum height.
            solidity: The minimum and maximum solidity of a contour.
            min_vertex_count: Minimum vertex Count of the contours.
            max_vertex_count: Maximum vertex Count.
            min_ratio: Minimum ratio of width to height.
            max_ratio: Maximum ratio of width to height.
        Returns:
            Contours as a list of numpy.ndarray.
        """
        output = []
        for contour in input_contours:
            x, y, w, h = cv2.boundingRect(contour)
            if w < min_width or w > max_width:
                continue
            if h < min_height or h > max_height:
                continue
            area = cv2.contourArea(contour)
            if area < min_area:
                continue
            if cv2.arcLength(contour, True) < min_perimeter:
                continue
            hull = cv2.convexHull(contour)
            solid = 100 * area / cv2.contourArea(hull)
            if solid < solidity[0] or solid > solidity[1]:
                continue
            if len(contour) < min_vertex_count or len(
                    contour) > max_vertex_count:
                continue
            ratio = (float)(w) / h
            if ratio < min_ratio or ratio > max_ratio:
                continue
            output.append(contour)
        return output


BlurType = Enum('BlurType',
                'Box_Blur Gaussian_Blur Median_Filter Bilateral_Filter')


def connectionListener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()


def extra_processing(pipeline):
    """
        Performs extra processing on the pipeline's outputs and publishes data to NetworkTables.
        :param pipeline: the pipeline that just processed an image
        :return: None
        """
    center_x_positions = []

    # Find the bounding boxes of the contours to get x, y, width, and height
    for contour in pipeline.filter_contours_output:
        x, y, w, h = cv2.boundingRect(contour)
        center_x_positions.append(x + w / 2)
    print('center x', center_x_positions)
    # Publish to the '/vision/red_areas' network table
    table = NetworkTables.getTable('datatable')
    table.putNumberArray('x', center_x_positions)


NetworkTables.initialize(server='10.28.32.2')
NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)

with cond:
    print("Waiting")
    if not notified[0]:
        cond.wait()

print("Connected!")
pipeline = ImgProc()

#Set table variable
tbl = NetworkTables.getTable('SmartDashboard')

#Set drivecam int
tbl.putNumber("camNumber", 1.0)
# 0 = Vision

#Get feed from camera/img
vision_cap = cv2.VideoCapture(0)
load_cap = cv2.VideoCapture(2)
climb_cap = cv2.VideoCapture(4)

#Setting the exposure
vision_cap.set(cv2.CAP_PROP_AUTO_EXPOSURE, 0.25)
vision_cap.set(cv2.CAP_PROP_EXPOSURE, -15)

#Vals for inRange function, targeting green
hue = [25, 90]  #'''DONT TOUCH'''
sat = [75, 255]  #'''DONT TOUCH'''
lum = [35, 150]  #'''DONT TOUCH'''

#Iterations for the erode function
erode_iters = 0

#Iterations for the dialte function
dil_iters = 20

#Counter variable for the loop
counter = 0
ticks_since = 0

#Dub window
dub_window = 30

#Skip val
skip_val = 1

#Height to target  Inches / 12 for feet
target_height = 69

#Camera offset angle for subtraction
cam_offset = 14

#Focal length
flength = 544

#cs_str = input("Start cameraserver? (y/n): ")
cs_str = 'y'
cs_bool = cs_str == 'y'
#Hey bucko
if cs_bool:
    #Start cameraserver instance
    cs = CameraServer.getInstance()
    cs.enableLogging()

    #Open output stream
    outputStream = cs.putVideo("OpenCV Camera", 320, 580)


def resImg(img, factor):
    scale_factor = factor

    width = int(img.shape[1] * scale_factor / 100)
    height = int(img.shape[0] * scale_factor / 100)
    dim = (width, height)
    img_resized = cv2.resize(img, dim, interpolation=cv2.INTER_AREA)

    return img_resized


while True:
    #Loop bools
    t_error = False
    i_error = False
    target_found = False
    #LED Pet project
    big_dub_x = False
    big_dub_y = False
    mega_dub = False

    #Take input from camera
    ret, img = vision_cap.read()

    #Center of the image
    if ret:
        pipeline.process(img)
        extra_processing(pipeline)

    img_to_push = cv2.add(img, np.array([150.0]))
    #Loop through found contours

    if cs_bool:
        print("Drive cam is ", tbl.getNumber("camNumber", 0))
        if tbl.getNumber("camNumber", 0) == 0:
            outputStream.putFrame(resImg(img_to_push, 50))
        elif tbl.getNumber("camNumber", 0) == 1.0:
            ret, load_frame = load_cap.read()
            print("Got cap")
            outputStream.putFrame(resImg(load_frame, 10))
        elif tbl.getNumber("camNumber", 0) == 2.0:
            ret, climb_frame = climb_cap.read()
            print("Got cap")
            outputStream.putFrame(resImg(climb_frame, 10))
        else:
            outputStream.putFrame(resImg(img_to_push, 30))
    # if t_error:
    #     print("TypeError")

    # if i_error:
    #     print("IndexError")

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

vision_cap.release()
load_cap.release()
climb_cap.release()
cv2.destroyAllWindows()
