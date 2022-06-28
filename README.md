# WordCounterV0

WordCounterV0   

A simple word count service.

1. The service assumes that word counting (**Note**English is assumed) is more frequent that adding new words.
2. The service also has an incomplete Translator service that converts words from other languages to english 

- A simple RestController is wrapped around the service in order to expose it.  
- To provide resilience, word storage would need to separated so that a proxy (such as nginx) can round robin. 
- Alternatively in a kubernities type environment a shared cache could be used, either way there needs to be a single source of truth for word storage.




