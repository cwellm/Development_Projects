#!/bin/bash
jlink --output PMChess --module-path 
/usr/lib/jvm/java-17-openjdk/lib/javafx.controls.jar
/usr/lib/jvm/java-17-openjdk/lib/javafx.fxml.jar
/usr/lib/jvm/java-17-openjdk/lib/javafx.graphics.jar
/usr/lib/jvm/java-17-openjdk/lib/javafx.media.jar
/home/christoph/Desktop/PostModernChess/Postmodern_Chess.jar 
/home/christoph/Desktop/PostModernChess/javafx.base.jar --add-modules javafx.base,javafx.graphics,javafx.controls,javafx.fxml Postmodern_Chess.jar java.base


