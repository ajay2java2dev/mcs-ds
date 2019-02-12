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
from sklearn import preprocessing
from sklearn.svm import SVC


datapath = 'data-bounded/'
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
    mndata = MNIST('data-bounded')
    trn_images, trn_labels = mndata.load_training()
    tst_images, tst_labels = mndata.load_testing()
    
    print('testing ... trn_images size: {0}, tst_images size: {1}, trn_labels size: {2}, tst_labels size: {3}'
    .format(len(trn_images),len(tst_images),len(trn_labels),len(tst_labels)))
    
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

''''
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
'''

def resizeAndHogFeatures(features,labels):
    list_hog_ft = []
    for feature in features:
        ft = hog (feature.reshape((28,28)), orientations=9, pixels_per_cell=(14,14),cells_per_block=(1,1),visualize=False)
        list_hog_ft.append(ft)
    hog_features = np.array(list_hog_ft,'float64')
    
    pp = preprocessing.StandardScaler().fit(hog_features)
    hog_features = pp.transform(hog_features)

    clf = SVC(C=5,gamma=.05)
    print(clf.fit(hog_features, labels))
    print(clf.score(hog_features, labels))

if __name__ == "__main__":
    downloadAndExtractDigitFiles()
    trn_images, trn_labels, tst_images , tst_labels = readMnistDigitData()    
    
    xTrain = np.array(trn_images)
    yTrain = np.array(trn_labels)

    xTest = np.array(tst_images)
    yTest = np.array(tst_labels)
   
    resizeAndHogFeatures(xTrain,yTrain)
    resizeAndHogFeatures(xTest,yTest)
