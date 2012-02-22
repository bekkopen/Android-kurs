Oppsett av miljø for Android-kurs
=========================

----
* 1. Eclipse
	- Eclipse Classic: http://www.eclipse.org/downloads/
 	- Husk evt proxy-settings hvis nødvendig: eclipse-proxy (eclipse preferences, søk etter proxy)
* 2. Android SDK
	- http://developer.android.com/sdk/index.html
	- Start SDK manager, huk av for Android SDK Tools, Android SDK Plattform-tools og Android 4.0.3 (API15) og velg "Install packages". MERK Proxy-settings hvis nødvendig: SDK-manager, tools-options
* 3. Android Development Toolkit
	- Eclipse: Install new software -> add "https://dl-ssl.google.com/android/eclipse/", velg alt under "Developer Tools", denne kan ta litt tid så vær tolmodig og den spør muligens om du godkjenner uaotirsert plugins etc.
* 4. Git  
	- http://code.google.com/p/msysgit/downloads/detail?name=Git-1.7.9-preview20120201.exe&can=3&q=
	- Proxy hvis nødvendig, Windows set https_proxy=http://ip:port eller unix export https_proxy=http://ip:port
	- Prøv å klone fra "https://github.com/bekkopen/Android-kurs": git clone "https://github.com/bekkopen/Android-kurs"
	- For å bytte mellom branch bruk: git checkout branch-navn

Troubleshooting
----
* Får ikke kompilert koden pga bruk av @Override av metoder fra Interfaces
	- Påse at eclipse benytter Java1.6 til kompilering: høyreklikk project -> Properties -> Java Compiler -> Compiler compliance level -> 1.6
* Feil ved installasjon av Android Development Toolkit
 	- prøv å kjøre Eclipse som Administrator hvis Windows 7
* Feilmelding når man laster ned API-15 ved skriving til c:\program... i windows, 
	- prøv å kjør som Administrator i Windows7 
