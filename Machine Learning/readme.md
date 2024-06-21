# Mental State Classification with Pre-Trained BERT model

## Machine Learning Path

This document outlines the steps taken to develop a sentiment analysis model using BERT and TensorFlow. The model classifies text into positive, negative, or neutral sentiments. Below are the key steps involved in the machine learning pipeline.

### Table of Contents
1. [Data Preprocessing](#data-preprocessing)
2. [Model Development](#model-development)
3. [Model Training](#model-training)
4. [Model Saving and Loading](#model-saving-and-loading)
5. [Model Serving](#model-serving)
6. [Deployment](#deployment)

### Data Preprocessing

We began by preprocessing the textual data to prepare it for training the BERT model. The preprocessing steps included:

- **Text Cleaning**: Removing puctuation, number, special character, white space, and stop words to remove noise.
- **Tokenization**: Converting text into tokens using the BERT tokenizer.
- **Padding**: Ensuring all input sequences are of the same length by padding shorter sequences.
- **Attention Masks**: Creating attention masks to differentiate between real tokens and padding tokens.

### Model Development

We used TensorFlow and the pre-trained BERT model to build the sentiment analysis model. The model architecture included BERT layers followed by dense layers for classification. The detailed architecture is as follows:

- **Input Layers**: Defined input layers for `input_ids` and `attention_mask` with a maximum length of 53.
- **BERT Embeddings**: Used the BERT model to obtain embeddings from the input data.
- **Global Max Pooling Layer**: Applied to the embeddings to reduce dimensionality.
- **Dense Layers**: Included dense layers with ReLU activation and dropout for regularization.
- **Output Layer**: Final dense layer with sigmoid activation to classify into five categories.

### Model Training

The model was compiled and trained using the following configurations:
- **Optimizer**: Adam optimizer with learning rate of 5e-5
- **Loss Function**: Categorical Crossentropy with logits.
- **Metrics**: balanced accuracy.

The training involved the following steps:
- **Inputs**: Defined input layers for `input_ids` and `attention_mask` with a maximum length of 53.
- **Embeddings**: Used the BERT model to obtain embeddings, applied a global max pooling layer, followed by dense layers with dropout for regularization.
- **Output**: The final layer used a sigmoid activation function to classify into five categories.

### Model Saving and Loading

After training, the model was saved for later use. This included saving the model architecture and weights separately:
- **Tokenizer**: Saved using `save_pretrained`.
- **Model Architecture**: Saved using `model.save`.
- **Model Weights**: Saved using `model.save_weights`.

The outputs are in this [folder](https://drive.google.com/drive/folders/101hB-HU4BsIUa5PgThwaGewjb6IC8oEB)

### Model Serving

To serve the model for real-time inference, the trained model was integrated into a service pipeline. This involved setting up an API using Cloud Run to handle input text and return sentiment predictions.

### Deployment

The deployment was done using Google Cloud Run for a scalable and managed serverless environment. This allowed us to deploy the model with the following considerations:
- **API Endpoint**: Created to handle inference requests.
- **Scalability**: Leveraged Cloud Run's auto-scaling feature to manage increasing loads.
- **Integration**: Ensured seamless integration with the preprocessing pipeline and real-time inference capabilities.

### Conclusion

The machine learning path involved developing a accurate sentiment analysis model using BERT, preparing the data, training the model, and deploying it as a web service on Google Cloud Run and on mobile devices. This comprehensive approach ensures sentiment analysis capabilities across various platforms.

