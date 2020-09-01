# Recipe Scrapper
Simple app to test HSQLDB setup

This is a Springboot API to scrape a recipe from a give page.

# Usage

POST `/recipes/scrape`

```$json
{
    "url": "path/to/recipe"
}
```

# Todo

 * swap im-memory db for real db (mongodb, postgres, mysql, graph maybe?)
 * add memory bus