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
#import tensorflow as tf
#import cv2
import numpy as np
import math

from mnist import MNIST
from skimage.io import imsave
from skimage.feature import hog
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import cross_val_score
from datetime import datetime
from scipy import ndimage

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
    
    '''
    print ('sample train : {}'.format(trn_images[0]))
    print ('sample label : {}'.format(trn_labels[0]))
    
    index = random.randrange(0,len(trn_images)) #select any random index position from the dataset extracted.
    print (trn_images[0])
    print (trn_images[index])
    print(mndata.display(trn_images[index]))    
    index = random.randrange(0,len(tst_images))
    print(mndata.display(tst_images[index]))
    
    X = np.array(trn_images[0])
    print(X)
    Y = np.array(trn_labels[1])
    print(Y)
    '''

    return trn_images, trn_labels, tst_images , tst_labels


if __name__ == "__main__":
    downloadAndExtractDigitFiles()
    trn_images, trn_labels, tst_images , tst_labels = readMnistDigitData()    
    
    xTrain = np.array(trn_images)
    yTrain = np.array(trn_labels)

    xTest = np.array(tst_images)
    yTest = np.array(tst_labels)
    
    print('\n10 trees + 4 depth + untouched ...')
    rfc = RandomForestClassifier(n_jobs=-1, n_estimators=10,max_depth=4) # all cores, 10 trees, 4 depth
    print('\t {}'.format(rfc.fit(xTrain, yTrain)))
    scoreTrain = rfc.score(xTrain, yTrain)
    print('\tTraining ... 10 trees + 4 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTrain,scoreTrain.mean(),rfc.predict(xTrain)))
    scoreTest = rfc.score(xTest, yTest)
    print('\tTesting ... 10 trees + 4 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTest,scoreTest.mean(),rfc.predict(xTest)))
    cvs=cross_val_score(rfc,xTrain, yTrain,cv=10)
    print('\tcross val score : {}'.format(cvs))

    print('\n10 trees + 16 depth + untouched ...')
    rfc = RandomForestClassifier(n_jobs=-1, n_estimators=10,max_depth=16) # all cores, 10 trees, 4 depth
    print('\t {}'.format(rfc.fit(xTrain, yTrain)))
    scoreTrain = rfc.score(xTrain, yTrain)
    print('\tTraining ... 10 trees + 16 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTrain,scoreTrain.mean(),rfc.predict(xTrain)))
    scoreTest = rfc.score(xTest, yTest)
    print('\tTesting ... 10 trees + 16 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTest,scoreTest.mean(),rfc.predict(xTest)))
    cvs=cross_val_score(rfc,xTrain, yTrain,cv=10)
    print('\tcross val score : {}'.format(cvs))

    print('\n30 trees + 4 depth + untouched ...')
    rfc = RandomForestClassifier(n_jobs=-1, n_estimators=30,max_depth=4) # all cores, 10 trees, 4 depth
    print('\t {}'.format(rfc.fit(xTrain, yTrain)))
    scoreTrain = rfc.score(xTrain, yTrain)
    print('\tTraining ... 30 trees + 4 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTrain,scoreTrain.mean(),rfc.predict(xTrain)))
    scoreTest = rfc.score(xTest, yTest)
    print('\tTesting ... 30 trees + 4 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTest,scoreTest.mean(),rfc.predict(xTest)))
    cvs=cross_val_score(rfc,xTrain, yTrain,cv=10)
    print('\tcross val score : {}'.format(cvs))

    print('\n30 trees + 16 depth + untouched ...')
    rfc = RandomForestClassifier(n_jobs=-1, n_estimators=30,max_depth=16) # all cores, 10 trees, 4 depth
    print('\t {}'.format(rfc.fit(xTrain, yTrain)))
    scoreTrain = rfc.score(xTrain, yTrain)
    print('\tTraining ... 30 trees + 16 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTrain,scoreTrain.mean(),rfc.predict(xTrain)))
    scoreTest = rfc.score(xTest, yTest)
    print('\tTesting ... 30 trees + 16 depth + untouched : score {0} , mean {1}, prediction {2}'.format(scoreTest,scoreTest.mean(),rfc.predict(xTest)))
    cvs=cross_val_score(rfc,xTrain, yTrain,cv=10)
    print('\tcross val score : {}'.format(cvs))