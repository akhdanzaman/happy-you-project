# Use Python 3.9-slim as the base image
FROM python:3.9-slim

# Set the working directory
WORKDIR /app

# Copy requirements.txt to the working directory
COPY requirements.txt /app/requirements.txt

# Install the necessary packages from requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Install curl
RUN apt-get update && apt-get install -y curl

# Set environment variable for protocol buffers
ENV PROTOCOL_BUFFERS_PYTHON_IMPLEMENTATION=python

# Set environment variable to use tf_keras with TensorFlow
ENV TF_USE_LEGACY_KERAS=1

# Set environment variable for Google Application Credentials
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/capstone-api-426212-b1e477b78acb.json"

# Create a local directory for model files
RUN mkdir -p local_model/variables

# Download the model files during build time
RUN curl -o local_model/config.json https://storage.googleapis.com/happyoumodelbucket/bert-model/config.json && \
    curl -o local_model/keras_metadata.pb https://storage.googleapis.com/happyoumodelbucket/bert-model/keras_metadata.pb && \
    curl -o local_model/saved_model.pb https://storage.googleapis.com/happyoumodelbucket/bert-model/saved_model.pb && \
    curl -o local_model/special_tokens_map.json https://storage.googleapis.com/happyoumodelbucket/bert-model/special_tokens_map.json && \
    curl -o local_model/tokenizer.json https://storage.googleapis.com/happyoumodelbucket/bert-model/tokenizer.json && \
    curl -o local_model/tokenizer_config.json https://storage.googleapis.com/happyoumodelbucket/bert-model/tokenizer_config.json && \
    curl -o local_model/vocab.txt https://storage.googleapis.com/happyoumodelbucket/bert-model/vocab.txt && \
    curl -o local_model/variables/variables.data-00000-of-00001 https://storage.googleapis.com/happyoumodelbucket/bert-model/variables/variables.data-00000-of-00001 && \
    curl -o local_model/variables/variables.index https://storage.googleapis.com/happyoumodelbucket/bert-model/variables/variables.index

# Copy the entire project to the working directory
COPY . /app

# Expose port 8080 to the outside world
EXPOSE 8080

# Define the command to run the application
CMD ["python", "api/app.py"]
