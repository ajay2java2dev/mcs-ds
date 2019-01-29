import numpy as np
import pandas as pd
import csv, codecs, random, math
from mnist import MNIST

mndata = MNIST('data')
trn_images, trn_labels = mndata.load_training()
tst_images, tst_labels = mndata.load_testing()

print (trn_images)
