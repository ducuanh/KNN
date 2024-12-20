# k-Nearest Neighbors (k-NN) Classifier

## Overview

This project implements the **k-Nearest Neighbors (k-NN)** algorithm for classification. The program classifies data points from a test dataset (`test-set`) based on a training dataset (`train-set`) and a user-specified hyperparameter `k`. It also provides an interactive interface for classifying individual vectors in real-time.

The k-NN algorithm is a simple, non-parametric method used for classification and regression. It works by finding the `k` nearest training samples to a given test sample and determining its class based on majority voting.

---

## Features

1. **k-NN Classification:**
   - Classifies observations from a test dataset using the k-NN algorithm.
   - Computes the overall accuracy (accuracy percentage) of the classification.

2. **Interactive Classification:**
   - Allows the user to classify individual feature vectors interactively.
   - Displays the predicted class label based on the training dataset.

3. **Distance Metric:**
   - Uses Euclidean distance to measure similarity between data points.

---

## Implementation Details
**Classes:**

   - Atrributes: Represents a single data observation, with feature values and a class label.
   - TrainTestResults: Stores the distance between a test observation and a training observation, along with the corresponding class label.

**Methods:**

   - getData: Reads and parses CSV files into lists of Atrributes.
   - KNN: Implements the k-NN algorithm, performs classification, and calculates accuracy.
   - calculateDistance: Computes Euclidean distance between two observations.
   - odpowiedz: Determines the majority class among the nearest neighbors.
