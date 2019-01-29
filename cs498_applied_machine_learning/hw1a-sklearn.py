import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import csv, codecs, random, math

#1) load the csv file first.
def loadCsv(filename):
    with open(filename,'rb') as csvfile:
        reader = csv.reader(codecs.iterdecode(csvfile, 'utf-8'))
        patientList = list(reader)
    
    for row in range(len(patientList)):
        patientList[row] = [float(x) for x in patientList[row]]
    return patientList

#2) random split into train-test sets. (8:10 mentioned with 10 test-train sets)
def splitDataset(dataset, splitRatio):
	trainSize = int(len(dataset) * splitRatio)
	trainSet = []
	copy = list(dataset)
	while len(trainSet) < trainSize:
		index = random.randrange(len(copy))
		trainSet.append(copy.pop(index))
	return [trainSet, copy]

#3) Summarize the data set.
def seperateByClass(dataset):
    seperated = {}
    for row in range(len(dataset)):
        vector = dataset[row]
        if (vector[-1] not in seperated):
            seperated[vector[-1]] = []
        seperated[vector[-1]].append(vector)
    return seperated

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

#5) Summarize the dataset using zip function to group
def summarize (dataset):
    #print(set(zip(*dataset)))
    summaries = [(mean(attribute),stdev(attribute)) for attribute in zip(*dataset)]
    del summaries[-1]
    return summaries

#6) Summarize by class
def summarizeByClass(dataset):
    seperated = seperateByClass(dataset)
    summaries = {}
    for classValue, instances in seperated.items():
        summaries [classValue] = summarize(instances)
    return summaries


def main():
    splitRatio = 0.80
    filename = '../input/pima-indians-diabetes.csv'
    dataset = loadCsv(filename)
    trainingSet, testSet = splitDataset(dataset,splitRatio)
    
    print('\n training set : {}'.format(trainingSet))
    print('\n test set : {}'.format(testSet))
    summaries = summarizeByClass(trainingSet)

    print(summaries)


main()