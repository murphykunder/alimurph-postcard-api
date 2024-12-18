# AlimurphPostcard

Create personalized cards with heartfelt music and custom message for your loved ones. Once the card is created you will also get the option to export it in PDF format

## Technical Stack
* Frontend: Angular version 17.3.10, HTML, SCSS
* Backend: Spring boot version 3.3.5, Java 17, Thymeleaf
* Database: None

## Backend Application

* Setup the application locally:
    1. Setup environment variables:
        - ALIMURPH_POSTCARD_ASSETS_PATH = any local/server folder path where the images and fonts will be stored
        - ALIMURPH_POSTCARD_FRONTEND_URL = Frontend URL that will be passed to the CORS configuration to allow requests from this site.
        - ALIMURPH_POSTCARD_SECRET_KEY = secret key that is used to encrypt and hash the user data
    2. Run maven command: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

* Application properties (application.yml)
    - server.servlet.context-path = "/api/v1/". This will prefix reach route with "api/v1", better for managing future versions.

* Open API Specification
http://localhost:8088/api/v1/swagger-ui/index.html

* Security
    - SecurityConfig class
    - Since at this stage in the application, no user account is maintained, by defaults all routes are accessible.
    - sessionManagement is kept STATELESS, so that spring does not store any data in the HTTPsession or spring context.
    - With default settings of CORS, the frontend application will be blocked and not be able to call the backend api. To allow the frontend app to be able to call the backend app, we need to setup the CORS config. This is defined in BeansConfig class.

* Dockerfile


## Frontend Application

* Steps to run the frontend locally:
    1. Open the project in command terminal.
    2. Setup environment variable: 
        - `NG_APP_ALIMURPH_POSTCARD_API_URL` = backend api url ex: http://localhost:8088/api/v1
        - `NG_APP_TINYURL_API_KEY` = Bearer token to connect to TinyUrl Api
        - `NG_APP_TINYURL_API_URL` = TinyUrl API url
    2. Run `npm install` followed by `npm run start`.

* Packages installed
    1. Ng bootstrap: `ng add @ng-bootstrap/ng-bootstrap@16`
        - The 16 version is compatible with Angular 17
        - CSS directly imported in styles.scss file
        - Add "node_modules/bootstrap/dist/js/bootstrap.min.js" in angular.json under "scripts".
        - Add "node_modules/@popperjs/core/dist/umd/popper.min.js" in angular.json under "scripts".
    2. Ng Open API gen
        - `npm i ng-openapi-gen`
        - Required to build the HTTPservices using Open API specification
    3. FontAwesome Free
        - `npm i @fortawesome/fontawesome-free`
    4. Wavesurfer
        - `npm i wavesurfer.js`
        - To display the music wave in the post card form page
    5. CKEditor
        - `npm install ckeditor5`
		- `npm install @ckeditor/ckeditor5-angular`
        - To get a rich text editor for entering the messages
    6. Canva Confetti
        - `npm i canvas-confetti`
        - `npm install @types/canvas-confetti --save-dev`
        - To display confetti when card loads
    7. NGx Environment builder
        - `ng add @ngx-env/builder`
        - `ng g environments`
        - To configure environment variables for angular application
    8. cryptoJS
        - `npm i crypto-js`


## Frontend - Build HttpServices using OpenAPI
- In the UI app under src/app create a folder "openapi". Inside this folder create a file "openapi.json"
- Startup the backend API and open the link "http://localhost:8088/api/v1/swagger-ui/index.html" in browser.
- Click on "/api/v1/v3/api-docs" link. This will display the openapi specification. Click on "Pretty print". Copy this data and paste in the "openapi.json" file.
- Install package https://www.npmjs.com/package/ng-openapi-gen . This project is a NPM module that generates model interfaces and web service clients from the OpenApi 3 specification we mentioned in the `openapi.json` file. **Ensure to use the correct version compatible with angular version of the app.**
- In package.json file, under scripts, add the line `"api-gen": "ng-openapi-gen --input ./src/app/openapi/openapi.json --output ./src/app/api/services"`
- This command will read the openApi specification from `openapi.json` file and will generate the required models and services in the  "services" folder in the project.
- Once all the services are created, for them to work they will need HttpClient. Update the app.config.ts file to include `provideHttpClient()` provider.

## Frontend - CKEditor
- Refer guide https://ckeditor.com/docs/ckeditor5/latest/getting-started/installation/angular.html
- Created a custom validator to limit the number of characters - ckeditor.validator.ts.
- By definition, *a validation function should accept AbstractControl data type parameter as input argument and return ValidationErrors | null object. The ValidationErrors object can have any attributes*.
- In this file we define a function ckEditorMaxLength which will return the validator function to use on the form element. ckEditorMaxLength is itself not a validator function.

## Frontend - Environment variables
- `ng add @ngx-env/builder`
- This will create an environment types file env.d.ts in src folder
- By default variable `NODE_ENV` is present. Add your custom env variable ex: `readonly NG_APP_ALIMURPH_POSTCARD_API_URL: string;`
- The custom variable should start with `NG_APP_`
- Run `ng g environments` to create the environment files for dev and prod.
- Define the env variable here for exmaple:

        export const environment = {
            backend_api_url: import.meta.env.NG_APP_ALIMURPH_POSTCARD_API_URL
        };

## Deployed backend on Render

## Deployed frontend on Vercel
