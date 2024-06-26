# Reverso Java API

![logotype](https://i.imgur.com/Mxbpi3N.jpeg)

Java API wrapper to perform contextual translations, 
 find synonyms, voice text, conjugate verbs, and much more.

Unfortunately,  complete information about the
services used by Reverso is not available, though some are known.
They work and behave differently, so our main goal was to create 
a unified and simple API, the responses of which would have a clear 
structure and behavior.
## Navigation
-   [Main components](#main-components)
    -   [Response](#response)
    -   [Language](#language)
-   [Usage](#usage)
    -   [Standart workfolw](#standard-workflow)
    -   [getVoiceStream](#getvoicestream)
-   [Response status](#response-status)
    -   [Success](#success)
    -   [Failure](#failure)
-   [Credits](#credits)

## Main components
### Response
Abstract class Response declares the common structure of all responses 
from the server.

```
public abstract class Response {

    private boolean isOK;

    private String sourceLanguage;

    private String text;

    private String errorMessage;
    ...
    public String toJson();
}
```
It has an important `isOk` field, which informs us 
about the result of our request.
**Warning: Always check that `isOk` is true before pulling 
data from a Response object.** Otherwise, you 
risk getting a `NullPointerException`. [See details](#failure) 
<br><br>
It also implements the `String toJson()` method,
for obtaining the JSON representation of any received response.
### Language
For most methods, it is necessary to specify the language, whether for 
translation or synonym search. It is represented as an **enum** with a 
list of all supported languages.
```
public enum Language {
ARABIC("arabic", "ar", "ara", true),
GERMAN("german", "de", "ger", true),
ENGLISH("english", "en", "eng", true);
...
}
```
Each object in this enumeration has fields that help determine whether
a specific language is supported for a given functionality. The fields 
are arranged in the following sequence:

1) `String fullName` <br>
2) `String synonymName` <br>
3) `String translateName` <br>
4) `boolean conjugate`

If any of these fields are false or null, it indicates that the language
does not support the respective functionality.

For example:

`KOREAN("korean", null, "kor", false)` 

Since the second and fourth fields are **null** and **false**, 
respectively, this means that **synonym** search and verb 
**conjugation** are not available for *korean* language.

## Usage
The fundamental class is the static class **`Reverso`**.
It manages all interactions with the API.
## Standard workflow
To retrieve the necessary data, we must call one of the static methods 
of this class.

For example:
```java
ConjugationResponse conjugationResponse = Reverso.getWordConjugation
(Language.RUSSIAN,"идти");

SynonymResponse response = Reverso.getSynonyms(Language.ENGLISH,"world");
System.out.println(response.toJson());
```
In this example, we want to obtain the conjugation for the Russian verb 
'идти'. Additionally, we want to get synonyms for the English word 'world'.

We save each result in a variable, **whose data type is always consistent 
with the name of the called method**. We also print the result of the 
second method
to the console in JSON format.

Here's the output :
```
{
"isOK": true,
"sourceLanguage": "english",
"text": "world",
"errorMessage": "null",
"synonyms": {
"Noun": [
"globe",
"earth",
"planet",
"universe",
"orb",
...
]
}
}
```
Most methods are very similar,  some require two languages in their 
arguments, such as the translation method, which is logical. 

## getVoiceStream
However, there is a method that differs slightly from the others:

`static VoiceResponse getVoiceStream(Voice voice, String text)`

For this method, you should pass one of the objects from the Voice enum. 
The names of the objects and their fields can tell us the language they
speak, their gender, etc.
```
public enum Voice {
ARABIC_LEILA("Leila22k", "Arabic", "F"),
CZECH_ELISKA("Eliska22k", "Czech", "F"),
FRENCH_ANTOINE("Antoine22k", "French", "M"),
ITALIAN_CHIARA("Chiara22k", "Italian", "F"),
GERMAN_ANDREAS("Andreas22k", "German", "M"),
...
}
```
Among other information, the VoiceResponse contains a byte array that 
stores the audio file, which voices the required text. To get the audio 
data, you can use the following code:
```java
VoiceResponse voiceResponse = Reverso.getVoiceStream(Voice.US_ENGLISH_KAREN, "wonderful world");

byte[] mp3Data = voiceResponse.getMp3Data();
```
It should be noted that when serializing this object using the `toJson()`
method, the *byte[] mp3Data* field is ignored. However, in this class, we
can also get this field as a Base64 string by invoking 
the `getAudioAsBase64()` method.

## Response status
Each response object has a `boolean isOK` field, along with a 
corresponding getter method, which indicates the success of our
request. If successful, the field is always set to `true`, 
**ensuring that the object contains all necessary data.**
### Success
Here's an example of a successful response:
```json
{
"isOK": true,
"sourceLanguage": "english",
"text": "hello",
"targetLanguage": "russian",
"translation": "привет",
"contextTranslations": {
"My aunt said to tell you hello.": "Моя тётя просила передать вам привет.",
"I get in the car and say hello.": "Я сел в машину и сказал привет.",
"He said to tell his son hello.": "Он сказал передать привет своему сыну.",
...
}
}
```
### Failure
As mentioned earlier, always verify the `isOk()`
status before retrieving data from an object.
Otherwise, we may encounter a `NullPointerException`.

For example:
```java
if(response.isOK()){
var synonyms = response.getSynonyms();
}
else System.out.println(response.getErrorMessage());
```
In the event of a failure, `isOK` will always be set to **false**, 
and the object will not contain the results of the request. 
**It's important to note that in case of failure, you can always 
retrieve a detailed error message using the method** `String 
getErrorMessage()`. If the request is successful, 
this method, of course, returns `null`.

Here is an example of a failed request and the response in JSON format:
```java
SynonymResponse response = Reverso.getSynonyms(Language.SWEDISH, "Skön");
System.out.println(response.toJson());
```
And console output:
```json
{
"isOK": false,
"errorMessage": "Synonyms cannot be obtained for this language. A list of available languages can be found here: https://synonyms.reverso.net/synonym/",
"sourceLanguage": "swedish",
"text": "Skön"
}
```
## Credits

-   All data is provided by [reverso.net](https://reverso.net)
-   Author on Telegram [@bondar852](https://t.me/bondar852)