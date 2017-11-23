# Simple Google Play Crawler

This simple crawler extracts the title, link, average rating, number of ratings, number of 5-star ratings and number of 4-star ratings for the top free music and audio apps in Google Play. Then, it writes that information to an Excel file so it can be analyzed more easily. 

It extracts the links to every top free music and audio application and crawls one every 10 seconds. The waiting is done to avoid Google detecting it as an attacker or something similar and blocking it. Because of this, it takes about 10 minutes for the crawler to generate 

## How to run

Clone this repository and run the main in src/Crawler.java

## Additional Notes

I made this crawler while I was making an Android application to help local music fans discover local musicians as a university project. It was a useful analysis tool at that moment, but it might stop working sometime in the future. If it does and I haven't noticed, please tell me by emailing me at sergiomaderarangel@gmail.com so I can do something about it.