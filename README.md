# RetroCrawler
RetroCrawler provides [Retrofit][1] converters for deserializing Html using [jspoon][2] and [jsoup-annotations][3]. Both of them internally uses [Jsoup][4]. 

## Jspoon Converter
A default `Jspoon` instance will be created or one can be configured and passed to the
`RetroCrawlerJSpoonConverterFactory` to further control the deserialization. For more information on how to use Jspoon annotations refer [this][2]. 

### Download
```groovy
implementation 'com.github.annypatel.retrocrawler:converter-jspoon:1.0.0-alpha1'
```

### Usage
To use RetroCrawler Jspoon converter, just add converter when building your Retrofit instance.

	Retrofit retrofit = new Retrofit.Builder()
		.baseUrl("https://api.example.com")
		.addConverterFactory(RetroCrawlerJSpoonConverterFactory.create())
		.build();
		
## Jsoup-Annotations Converter
`JsoupProcessor` class from jsoup-annotations will be used by `RetroCrawlerJSoupAnnotationConverterFactory` for Html deserialization. For more information on how to use jsoup-annotations refer [this][3].

### Download
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.annypatel.retrocrawler:converter-jsoup-annotations:1.0.0-alpha1'
}
```

### Usage
To use RetroCrawler Jsoup-Annotations converter, just add converter when building your Retrofit instance.

	Retrofit retrofit = new Retrofit.Builder()
		.baseUrl("https://api.example.com")
		.addConverterFactory(RetroCrawlerJSoupAnnotationConverterFactory.create())
		.build();

[1]: https://github.com/square/retrofit
[2]: https://github.com/DroidsOnRoids/jspoon
[3]: https://github.com/fcannizzaro/jsoup-annotations
[4]: https://jsoup.org/