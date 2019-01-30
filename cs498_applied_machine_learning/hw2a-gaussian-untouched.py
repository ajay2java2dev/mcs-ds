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
from datetime import datetime
from future.utils import iteritems
from builtins import range, input
from scipy.stats import norm
from scipy.stats import multivariate_normal as mvn

datapath = 'data/'
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
    mndata = MNIST('data')
    trn_images, trn_labels = mndata.load_training()
    tst_images, tst_labels = mndata.load_testing()
    
    print('testing ... trn_images size: {0}, tst_images size: {1}, trn_labels size: {2}, tst_labels size: {3}'
    .format(len(trn_images),len(tst_images),len(trn_labels),len(tst_labels)))
    
    '''
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

class MultinomialNB(object):
    print ('Executing Naive Bayes bernoulli multinomial classification ...')
    def fit(self, X, Y, smoothing=1.0):
        K = len (set(Y)) #number of classes
        N = len (Y) #number of samples
        print('number of classes : {0}, number of samples : {1}'.format(K,N))

        labels = Y
        Y = np.zeros((N, K))
        Y[np.arange(N),labels] = 1

        feature_counts = X.T.dot(Y) + smoothing
        class_counts = Y.sum(axis=0)

        self.weights = np.log(feature_counts) - np.log(feature_counts.sum(axis=0))
        self.priors = np.log(class_counts) - np.log(class_counts.sum())

    def score(self, X, Y):
        P = self.predict(X)
        return np.mean(P == Y)
    
    def predict(self, X):
        P = X.dot(self.weights) + self.priors
        return np.argmax(P, axis=1)

class GaussianNB(object):
    print ('Executing Gaussian classification ...')
    def fit(self, X, Y, smoothing=1e-2):
        N, D = X.shape
        self.gaussians = dict()
        self.priors = dict()
        labels = set(Y)
        for c in labels:
            current_x = X[Y == c]
            self.gaussians[c] = {
                'mean': current_x.mean(axis=0),
                'cov': np.cov(current_x.T) + np.eye(D)*smoothing,
            }
            self.priors[c] = float(len(Y[Y == c])) / len(Y)

    def score(self, X, Y):
        P = self.predict(X)
        return np.mean(P == Y)

    def predict(self, X):
        N, D = X.shape
        K = len(self.gaussians)
        P = np.zeros((N, K))
        for c, g in iteritems(self.gaussians):
            mean, cov = g['mean'], g['cov']
            P[:,c] = mvn.logpdf(X, mean=mean, cov=cov) + np.log(self.priors[c])
        return np.argmax(P, axis=1)



def callNaiveBaiseBernoulliMultinomialClassficiation(xTrain,yTrain,xTest,yTest):
    #call classification...
    print('\nExecuting multinomial naive bayes classification : ')
    model = MultinomialNB()

    t0 = datetime.now()
    model.fit(xTrain,yTrain)
    print("\n\t\tTraining time:", (datetime.now() - t0))
    
    t0 = datetime.now()
    print('\n\t\tTrain accuracy ... : {}'.format(model.score(xTrain,yTrain)))
    print("\t\tTime to compute train accuracy:", (datetime.now() - t0), "Train size:", len(yTrain))

    t0 = datetime.now()
    print("\n\t\tTest accuracy:", model.score(xTest, yTest))
    print("\t\tTime to compute test accuracy:", (datetime.now() - t0), "Test size:", len(yTest))

def callNaiveBaiseGaussianClassficiation(xTrain,yTrain,xTest,yTest):
    #call classification...
    print('\nExecuting gaussian classification : ')
    model = GaussianNB()

    t0 = datetime.now()
    model.fit(xTrain,yTrain)
    print("\n\t\tTraining time:", (datetime.now() - t0))
    
    t0 = datetime.now()
    print('\n\t\tTrain accuracy ... : {}'.format(model.score(xTrain,yTrain)))
    print("\t\tTime to compute train accuracy:", (datetime.now() - t0), "Train size:", len(yTrain))

    t0 = datetime.now()
    print("\n\t\tTest accuracy:", model.score(xTest, yTest))
    print("\t\tTime to compute test accuracy:", (datetime.now() - t0), "Test size:", len(yTest))

if __name__ == "__main__":
    downloadAndExtractDigitFiles()
    trn_images, trn_labels, tst_images , tst_labels = readMnistDigitData()    
    
    xTrain = np.array(trn_images)
    yTrain = np.array(trn_labels)

    xTest = np.array(tst_images)
    yTest = np.array(tst_labels)
    
    callNaiveBaiseBernoulliMultinomialClassficiation(xTrain,yTrain,xTest,yTest)
    callNaiveBaiseGaussianClassficiation(xTrain,yTrain,xTest,yTest)