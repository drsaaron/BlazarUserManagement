FROM drsaaron/blazarjavabase:1.30

# create an app directory
ENV BLAZAR_APP_DIR=/home/blazar/blazarusermangement/app
WORKDIR $BLAZAR_APP_DIR
 
# add the source code
ADD ./pom.xml .
ADD ./src ./src
ADD ./mvnw ./mvnw
ADD ./.mvn ./.mvn
ADD ./runServices.sh ./runServices.sh

# build the application
RUN ./mvnw clean install -Dskip.it=true

# open the ports
EXPOSE 4500

# define this as the production environment
ENV ENVIRONMENT prod

# run the script
CMD ./runServices.sh
