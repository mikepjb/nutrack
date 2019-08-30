FROM clojure:openjdk-8-tools-deps

COPY . /nutrack

WORKDIR /nutrack

CMD clojure -A:run
