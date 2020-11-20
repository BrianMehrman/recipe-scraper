# Recipe Scrapper
Simple app to test HSQLDB setup

This is a Springboot API to scrape a recipe from a give page.


## Install dependencies
```
brew install skaffold
brew install kubernetes-cli
brew install kustomize
brew install k9s
```

## Build Docker image

```
docker build -t recipe-scraoer:0.0.1 . -f ./kubernetes/Dockerfile
```

If you build your image remember to update the `kubernetes/recipe-scraper/deployment.yaml` with the image name.

## Starting K8s

```
skaffold dev --port-forward
```

# Usage

POST `/recipes/scrape`

```$json
{
    "url": "path/to/recipe"
}
```

### Example

```
curl --location --request POST 'http://localhost:9101/v1/recipes/scrape' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "https://www.foodnetwork.com/recipes/food-network-kitchen/jambalaya-3362212"
}'
```


# Todo

 * swap im-memory db for real db (mongodb, postgres, mysql, graph maybe?)
 * add memory bus
