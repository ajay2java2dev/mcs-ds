# This Python 3 environment comes with many helpful analytics libraries installed
# It is defined by the kaggle/python docker image: https://github.com/kaggle/docker-python
# For example, here's several helpful packages to load in 

import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import csv, codecs, random, math
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import train_test_split
from sklearn import svm
from sklearn import datasets

# Input data files are available in the "../input/" directory.
# For example, running this (by clicking run or pressing Shift+Enter) will list the files in the input directory

import os
print(os.listdir("../input"))

# Any results you write to the current directory are saved as output.

#1) load the csv file first.
def loadCsv(filename):
    with open(filename,'rb') as csvfile:
        reader = csv.reader(codecs.iterdecode(csvfile, 'utf-8'))
        patientList = list(reader)
    
    for row in range(len(patientList)):
        patientList[row] = [float(x) for x in patientList[row]]
    return patientList

#filename = '../input/pima-indians-diabetes.csv'
#dataset  = loadCsv(filename)
#print('Loaded data file {0} with {1} rows'.format(filename, len(dataset)))

#2) random split into train-test sets. (8:10 mentioned with 10 test-train sets)
def splitDataset(dataset, splitRatio):
	trainSize = int(len(dataset) * splitRatio)
	trainSet = []
	copy = list(dataset)
	while len(trainSet) < trainSize:
		index = random.randrange(len(copy))
		trainSet.append(copy.pop(index))
	return [trainSet, copy]
	
#dataset = [[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]]
#splitRatio = 0.80
#train, test = splitDataset(dataset, splitRatio)
#print('Split {0} rows into train with {1} and test with {2}'.format(len(dataset), train, test))

#3) Summarize the data set.
def seperateByClass(dataset):
    seperated = {}
    for row in range(len(dataset)):
        vector = dataset[row]
        if (vector[-1] not in seperated):
            seperated[vector[-1]] = []
        seperated[vector[-1]].append(vector)
    return seperated

#dataset1 = [[1,20,1],[2,21,0],[3,22,1]]
#seperated = seperateByClass(dataset1)
#print('Seperated instances: {0}'.format(seperated))

#4) Calculating the mean, variance and therby the standard deviation.
def mean(numbers):
    return sum(numbers)/float(len(numbers))
    
def stdev(numbers):
    avg = mean(numbers)
    variance = sum([pow(x-avg,2) for x in numbers]) / float (len(numbers)-1)
    #print(sum([pow(x-avg,2) for x in numbers]))
    #print(float (len(numbers)-1))
    #print(variance)
    return math.sqrt(variance)
    
#numbers=[1,2,3,4,5]
#print('Summary of {0}: mean={1}, standard deviation={2}'.format(numbers, mean(numbers), stdev(numbers)))

#5) Summarize the dataset using zip function to group
def summarize (dataset):
    #print(set(zip(*dataset)))
    summaries = [(mean(attribute),stdev(attribute)) for attribute in zip(*dataset)]
    del summaries[-1]
    return summaries
    
#dataset = [[1,20,0],[2,21,1],[3,22,0]]
#summary = summarize(dataset)
#print('summaries {0}'.format(summary))

#6) Summarize by class
def summarizeByClass(dataset):
    seperated = seperateByClass(dataset)
    summaries = {}
    for classValue, instances in seperated.items():
        summaries [classValue] = summarize(instances)
    return summaries

#dataset = [[1,20,1], [2,21,0], [3,22,1], [4,22,0]]
#summary = summarizeByClass(dataset)
#print('Summary by class value: {0}'.format(summary))

#Calculate Gaussian Probability Density Function
def calculateProbability(x, mean, stdev):
	exponent = math.exp(-(math.pow(x-mean,2)/(2*math.pow(stdev,2))))
	return (1 / (math.sqrt(2*math.pi) * stdev)) * exponent
 
def calculateClassProbabilities(summaries, inputVector):
	probabilities = {}
	for classValue, classSummaries in summaries.items():
		probabilities[classValue] = 1
		for i in range(len(classSummaries)):
			mean, stdev = classSummaries[i]
			x = inputVector[i]
			probabilities[classValue] *= calculateProbability(x, mean, stdev)
	return probabilities
			
def predict(summaries, inputVector):
	probabilities = calculateClassProbabilities(summaries, inputVector)
	bestLabel, bestProb = None, -1
	for classValue, probability in probabilities.items():
		if bestLabel is None or probability > bestProb:
			bestProb = probability
			bestLabel = classValue
	return bestLabel
 
def getPredictions(summaries, testSet):
	predictions = []
	for i in range(len(testSet)):
		result = predict(summaries, testSet[i])
		predictions.append(result)
	return predictions
 
def getAccuracy(testSet, predictions):
	correct = 0
	for i in range(len(testSet)):
		if testSet[i][-1] == predictions[i]:
			correct += 1
	return (correct/float(len(testSet))) * 100.0

def accuracyHW1a():
	splitRatio = 0.80
	filename = '../input/pima-indians-diabetes.csv'
	dataset = loadCsv(filename)
	total = 0
	for i in range(10):
		trainingSet, testSet = splitDataset(dataset,splitRatio)
		summaries = summarizeByClass(trainingSet)
		predictions = getPredictions(summaries, testSet)
		accuracy = getAccuracy (testSet, predictions)
		#print('Accuracy for {1}: {0}%'.format(accuracy,i))
		total=total+accuracy		
	print('Hw1a: Average over 10 test-train splits: {}'.format(total/10))

accuracyHW1a()

def accuracyHW1b():
	splitRatio = 0.80
	filename = '../input/pima-indians-diabetes.csv'
	dataset = loadCsv(filename)
	a = [(a,b,c,d,e,f,g,h,i) for a,b,c,d,e,f,g,h,i in dataset if c>0 and d > 0 and f >0 and h>0]
	#print('{}'.format(a))
	#print('count dataset: {}, and after filter {}'.format(len(dataset),len(a)))

	total = 0
	for i in range(10):
		trainingSet, testSet = splitDataset(a,splitRatio)
		summaries = summarizeByClass(trainingSet)
		predictions = getPredictions(summaries, testSet)
		accuracy = getAccuracy (testSet, predictions)
		#print('Accuracy for {1}: {0}%'.format(accuracy,i))
		total=total+accuracy		
	print('Hw1b: Average over 10 test-train splits (ignored rows with 0): {}'.format(total/10))

accuracyHW1b()
