# Andromeda

Andromeda is a bare minimum spring boot service to manage Android Emulator docker containers .
At present, this service supports the images available at [budtmo/docker-android](https://github.com/budtmo/docker-android)


## Installation


```bash
git clone https://github.com/rupinr/andromeda.git
./gradlew clean bootJar

```

## Usage

```bash
java -jar build/libs/andromeda-0.0.1-SNAPSHOT.jar 

For real world usage, it is recommended to run this as a systemctl service.

A monitoring page will be available at http://localhost:8080/andromeda/index.html
 
```
![Monitoring Page](https://github.com/rupinr/andromeda/blob/master/docs/screenshot.png)



For the rest endpoints, refer the swagger [definition](https://app.swaggerhub.com/apis-docs/rupinr/andromeda/0.1#/)



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
