package org.example;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ImageGenerator {



    private JSONObject formPrompt(String prompt){
        String promptText = """
                {
                          "19": {
                            "inputs": {
                              "ckpt_name": "dreamshaperXL_v21TurboDPMSDE.safetensors"
                            },
                            "class_type": "CheckpointLoaderSimple",
                            "_meta": {
                              "title": "Load Checkpoint"
                            }
                          },
                          "20": {
                            "inputs": {
                              "width": 720,
                              "height": 1280,
                              "batch_size": 4
                            },
                            "class_type": "EmptyLatentImage",
                            "_meta": {
                              "title": "Empty Latent Image"
                            }
                          },
                          "21": {
                            "inputs": {
                              "vae_name": "sdxlVAE_sdxlVAE.safetensors"
                            },
                            "class_type": "VAELoader",
                            "_meta": {
                              "title": "Load VAE"
                            }
                          },
                          "22": {
                            "inputs": {
                              "text": "%s",
                              "clip": [
                                "24",
                                0
                              ]
                            },
                            "class_type": "CLIPTextEncode",
                            "_meta": {
                              "title": "CLIP Text Encode (Prompt)"
                            }
                          },
                          "23": {
                            "inputs": {
                              "text": "low quality, blur, human",
                              "clip": [
                                "24",
                                0
                              ]
                            },
                            "class_type": "CLIPTextEncode",
                            "_meta": {
                              "title": "CLIP Text Encode (Prompt)"
                            }
                          },
                          "24": {
                            "inputs": {
                              "stop_at_clip_layer": -1,
                              "clip": [
                                "19",
                                1
                              ]
                            },
                            "class_type": "CLIPSetLastLayer",
                            "_meta": {
                              "title": "CLIP Set Last Layer"
                            }
                          },
                          "25": {
                            "inputs": {
                              "seed": 949103755562205,
                              "steps": 20,
                              "cfg": 3.8000000000000003,
                              "sampler_name": "dpmpp_2m",
                              "scheduler": "karras",
                              "denoise": 1,
                              "model": [
                                "19",
                                0
                              ],
                              "positive": [
                                "22",
                                0
                              ],
                              "negative": [
                                "23",
                                0
                              ],
                              "latent_image": [
                                "20",
                                0
                              ]
                            },
                            "class_type": "KSampler",
                            "_meta": {
                              "title": "KSampler"
                            }
                          },
                          "26": {
                            "inputs": {
                              "samples": [
                                "25",
                                0
                              ],
                              "vae": [
                                "21",
                                0
                              ]
                            },
                            "class_type": "VAEDecode",
                            "_meta": {
                              "title": "VAE Decode"
                            }
                          },
                          "29": {
                            "inputs": {
                              "filename_prefix": "ComfyUI",
                              "images": [
                                "26",
                                0
                              ]
                            },
                            "class_type": "SaveImage",
                            "_meta": {
                              "title": "Save Image"
                            }
                          }
                        }
            """.formatted(prompt);

        JSONObject promptJSON = new JSONObject(promptText);

        return promptJSON;
    }

    public static void retrieveAndSaveImage(String promptId, String outputFilePath) {
        try {
            // Construct the URL to retrieve the image associated with the prompt_id
            String imageUrl = "http://127.0.0.1:8188/results/" + promptId + "/image.png"; // Adjust this URL according to your server's API

            // Open a connection to the URL
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "image/png");

            // Check for successful response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Save the image to the specified file path
                try (InputStream inputStream = connection.getInputStream()) {
                    Files.copy(inputStream, Paths.get(outputFilePath));
                    System.out.println("Image saved to " + outputFilePath);
                }
            } else {
                System.out.println("Failed to retrieve image. Response Code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queuePrompt(JSONObject prompt) {
        try {
            // Prepare the request
            URL url = new URL("http://127.0.0.1:8188/prompt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Convert the prompt to a JSON object with a "prompt" key
            Map<String, JSONObject> dataMap = new HashMap<>();
            dataMap.put("prompt", prompt);
            JSONObject json = new JSONObject(dataMap);

            // Write JSON data to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
            } catch (IOException e) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    System.out.println("Error Response: " + errorResponse.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateImage(String prompt)
    {
        JSONObject promptJSON = formPrompt(prompt);
        queuePrompt(promptJSON);
        System.out.println("Generating image.");
    }
}