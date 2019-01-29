'''
The coding for this excerciese is completed using multiple libraries and sample implementation 
found online and clubbed together as per the exercise requirements.
'''

import numpy as np
import pandas as pd
import csv, codecs, random, math
from mnist import MNIST

#The mnist 
def downldAndExtrctDigitFiles():
    
def readMnistDigitData():
    mndata = MNIST('data')
    trn_images, trn_labels = mndata.load_training()
    tst_images, tst_labels = mndata.load_testing()
    print (trn_images)

def main():


main()
