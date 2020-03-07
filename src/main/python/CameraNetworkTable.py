import threading
import time
import cv2
from networktables import NetworkTables
from grip import ImgProc

cond = threading.Condition()
notified = [False]

def connectionListener(connected, info):
    
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

NetworkTables.initialize(server='10.28.32.2')
NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)

with cond:
    print("Waiting")
    if not notified[0]:
        cond.wait()
def extra_processing(pipeline):
        """
        Performs extra processing on the pipeline's outputs and publishes data to NetworkTables.
        :param pipeline: the pipeline that just processed an image
        :return: None
        """
        center_x_positions = []
        #center_y_positions = []
        #widths = []
        #heights = []

        # Find the bounding boxes of the contours to get x, y, width, and height
        for contour in pipeline.filter_contours_output:
            x, y, w, h = cv2.boundingRect(contour)
            center_x_positions.append(x + w / 2)  # X and Y are coordinates of the top-left corner of the bounding box
            #center_y_positions.append(y + h / 2)
            #widths.append(w)
            #heights.append(h)
        print('center x', center_x_positions)
        #('center y', center_y_positions)
        #print('center y', center_y_positions)
        # Publish to the '/vision/red_areas' network table
        table = NetworkTables.getTable('datatable')
        table.putNumberArray('x', center_x_positions)
        #table.putNumberArray('y', center_y_positions)
        #table.putNumberArray('width', widths)
        #table.putNumberArray('height', heights)

print('Creating video capture')
cap = cv2.VideoCapture(0)
print('Creating pipeline')
pipeline = ImgProc()
while cap.isOpened():
    have_frame, frame = cap.read()
    if have_frame:
        pipeline.process(frame)
        extra_processing(pipeline)
print("Connected!")