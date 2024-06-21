
---

# Emotion Detection API

This API is designed to detect emotions from text input. It uses a BERT model hosted on Google Cloud Storage, with a Flask-based backend and data stored in Firestore. Authentication is handled using PyJWT.

## Base URL

`https://emotion-detection-api**********-et.a.run.app`

## Endpoints

### 1. **Register User**

**Endpoint:** `/auth/register`

**Method:** `POST`

**Description:** Registers a new user.

**Request Parameters:**

- `username` (string): The username of the new user.
- `password` (string): The password for the new user.

**Response:**

- `200 OK`: Registration successful.
- `400 Bad Request`: Missing or invalid parameters.

### 2. **Login User**

**Endpoint:** `/auth/login`

**Method:** `POST`

**Description:** Authenticates a user and returns a JWT token.

**Request Parameters:**

- `username` (string): The username of the user.
- `password` (string): The password of the user.

**Response:**

- `200 OK`: Login successful, returns JWT token.
- `401 Unauthorized`: Invalid username or password.

### 3. **Save Tweet**

**Endpoint:** `/save-tweet`

**Method:** `POST`

**Description:** Saves a tweet with detected emotion.

**Request Headers:**

- `Authorization` (string): Bearer token for authenticated requests.

**Request Parameters:**

- `text` (string): The text of the tweet to analyze.

**Response:**

- `200 OK`: Tweet saved successfully.
- `400 Bad Request`: Missing text parameter.
- `401 Unauthorized`: Invalid or missing token.

### 4. **Get Tweets by User ID**

**Endpoint:** `/get-tweet-byuserid`

**Method:** `GET`

**Description:** Retrieves all tweets for a specific user.

**Request Parameters:**

- `user_id` (string): The ID of the user.

**Response:**

- `200 OK`: Returns a list of tweets.
- `400 Bad Request`: Missing user ID.

### 5. **Predict Emotion**

**Endpoint:** `/predict-emotion`

**Method:** `POST`

**Description:** Analyzes the text and returns the detected emotion.

**Request Parameters:**

- `text` (string): The text to analyze.

**Response:**

- `200 OK`: Returns the detected emotion, encouraging message, and activity suggestion.
- `400 Bad Request`: Missing text parameter.

## Authentication

This API uses JWT for authentication. Include the token in the `Authorization` header for all authenticated endpoints.

```
Authorization: Bearer <jwt-token>
```

## Example Requests

### **Register User**

```bash
curl -X POST https://emotion-detection-api**********-et.a.run.app/auth/register -d "username=testuser" -d "password=testpass"
```

### **Login User**

```bash
curl -X POST https://emotion-detection-api**********-et.a.run.app/auth/login -d "username=testuser" -d "password=testpass"
```

### **Save Tweet**

```bash
curl -X POST https://emotion-detection-api**********-et.a.run.app/save-tweet -H "Authorization: Bearer <your-jwt-token>" -d "text=I am feeling great today!"
```

### **Get Tweets by User ID**

```bash
curl -X GET "https://emotion-detection-api**********-et.a.run.app/get-tweet-byuserid?user_id=<user-id>"
```

### **Predict Emotion**

```bash
curl -X POST https://emotion-detection-api**********-et.a.run.app/predict-emotion -d "text=I am feeling very happy today!"
```

---
