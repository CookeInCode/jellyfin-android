A customized version of the popular Android client application tailored specifically for Jellyfin users, **users!**

Changes from Official Repository;
  - Distinguishes itself clearly from the official version when installed concurrently.
  - Offers auto-suggestion/population of a default server addresses during setup (requires the use of Android Studio to modify the auto-suggestion server address).

Android Client Banner Update:
  - Due to the Android client's banner retrieval method, a themed replacement banner has been added to the release section. For example, to replace via docker-compose you would add to the volumes section;

    - /jellyfin/banner/banner.png:/usr/share/jellyfin/web/assets/img/banner-dark.png  
    - /jellyfin/banner/banner.png:/usr/share/jellyfin/web/assets/img/banner-light.png
  
**Mobile**

<a href="https://ibb.co/fnRScfD"><img src="https://i.ibb.co/3MXsJ2R/Screenshot-20240219-122009-Jellyfin.png" border="0"></a>

<a href="https://ibb.co/ZHjj456"><img src="https://i.ibb.co/WDhh8ZW/Screenshot-20240219-122056-Jellyfin.png" border="0"></a>
