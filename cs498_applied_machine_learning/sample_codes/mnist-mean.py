'''
The coding for this excerciese is completed using multiple libraries and sample implementation 
found online and clubbed together as per the exercise requirements.
'''

from __future__ import print_function, division
import os,urllib.request
import numpy as np
import pandas as pd
import csv, codecs, random, math, gzip,shutil
import matplotlib.pyplot as plt

from mnist import MNIST
from skimage.io import imsave
from skimage.feature import hog
from datetime import datetime
from future.utils import iteritems
from builtins import range, input
from scipy.stats import norm
from scipy.stats import multivariate_normal as mvn

datapath = 'data-unbounded/'
def downloadAndExtractDigitFiles():
    if not os.path.exists(datapath):
        os.makedirs(datapath)
    
    urls = ['http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz',
       'http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz']
    
    for url in urls:
        filename = url.split('/')[-1]   # GET FILENAME
        if os.path.exists(datapath+filename):
            print(filename, ' already exists')  # CHECK IF FILE EXISTS
        else:
            print('Downloading ',filename)
            urllib.request.urlretrieve (url, datapath+filename) # DOWNLOAD FILE
    
        print('All files are available')

        files = os.listdir(datapath)
        for file in files:
            if file.endswith('gz'):
                print('Extracting ',file)
                with gzip.open(datapath+file, 'rb') as f_in:
                    with open(datapath+file.split('.')[0], 'wb') as f_out:
                        shutil.copyfileobj(f_in, f_out)
        print('Extraction Complete')   

def readMnistDigitData():
    mndata = MNIST('data-unbounded')
    trn_images, trn_labels = mndata.load_training()
    tst_images, tst_labels = mndata.load_testing()
    
    print('testing ... trn_images size: {0}, tst_images size: {1}, trn_labels size: {2}, tst_labels size: {3}'
    .format(len(trn_images),len(tst_images),len(trn_labels),len(tst_labels)))
    
    return trn_images, trn_labels, tst_images , tst_labels

def resize(xs):
    N,dim=xs.shape
    if (dim!=28*28):
        print ('wrong size --> {}'.format(dim))
        return
    xs_new = np.zeros((N,14*14),dtype='float32')
    for i in range(N):
        xi = xs[i].reshape(28,28)
        xs_new[i] = xi[::2,::2].reshape(14*14)
    return xs_new

if __name__ == "__main__":
    downloadAndExtractDigitFiles()
    trn_images, trn_labels, tst_images , tst_labels = readMnistDigitData()
    xTrain = np.array(trn_images)
    yTrain = np.array(trn_labels)

    xTest = np.array(tst_images)
    yTest = np.array(tst_labels)

    xsTrainNew = resize(xTrain)
    

