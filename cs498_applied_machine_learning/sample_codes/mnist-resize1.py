#I am using this class to load the MNIST dataset.

"""Functions for downloading and reading MNIST data."""
import gzip
import os
import tempfile

import numpy
import tensorflow as tf
from tensorflow.contrib.learn.python.learn.datasets.mnist import read_data_sets

import os,urllib.request
import gzip,shutil,codecs,numpy
import cv2
import tensorflow as tf


from skimage.io import imsave

datapath = 'data/'

for no in [0]:
    gray = cv2.imread("data/train/"+str(no)+"/00000.png",0)
    gray = cv2.resize(255-gray, (20, 20))
    print(gray)