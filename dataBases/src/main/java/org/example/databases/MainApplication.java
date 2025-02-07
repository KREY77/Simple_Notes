package org.example.databases;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainApplication extends Application {
    User loginnedUser;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(getAuthorizationScene(stage));
        stage.show();
    }
    public Scene getAuthorizationScene(Stage stage){
        VBox vbox = new VBox(10);
        TabPane tabPane = new TabPane();
        Tab login = new Tab("Login");
        VBox vLogin = new VBox(10);
        Label usernameL = new Label("Username:");
        Label passwordL = new Label("Password:");
        TextField forUsernameL = new TextField();
        TextField forPasswordL = new TextField();
        Button signIn = new Button("Login");
        signIn.setOnAction(e->{
        String username = forUsernameL.getText();
        String password = forPasswordL.getText();
        User user = UserDAO.getByUsername(username);
        if(user!=null){
            if(user.getPassword().equals(password)){
            loginnedUser = user;
            stage.close();
            Scene sceneLoginned = getNotesScene();
            Stage noteStage = new Stage();
            noteStage.setScene(sceneLoginned);
            noteStage.show();
            }
        }
        });
        vLogin.getChildren().addAll(usernameL,forUsernameL,passwordL,forPasswordL,signIn);
        login.setContent(vLogin);
        Tab registr= new Tab("Registration");
        VBox vRegistr = new VBox(10);
        Label usernameR = new Label("Username:");
        Label passwordR = new Label("Password:");
        Label emailR = new Label("Email:");
        TextField forUsernameR = new TextField();
        TextField forPasswordR = new TextField();
        TextField forEmailR = new TextField();
        Button signUP = new Button("Sign up");
        signUP.setOnAction(e->{
            if(!forUsernameR.getText().isEmpty()&&!forPasswordR.getText().isEmpty()&&!forEmailR.getText().isEmpty()) {
                String username = forUsernameR.getText();
                String password = forPasswordR.getText();
                String email = forEmailR.getText();
                User check = UserDAO.getByUsername(username);
                if (check == null) {
                    User user = new User(username, password, email, "USER");
                    UserDAO.addUser(user);
                }
            }
        });
        vRegistr.getChildren().addAll(usernameR,forUsernameR,passwordR,forPasswordR,emailR,forEmailR,signUP);
        registr.setContent(vRegistr);
        tabPane.getTabs().addAll(login,registr);
        vbox.getChildren().addAll(tabPane);
        Scene scene = new Scene(vbox, 400, 400);
        return scene;
    }


    public Scene getNotesScene(){
        VBox vBox = new VBox(10);
        ListView <HBox> listView = new ListView<>();
        updateNoteList(listView);
        Button addNote = new Button("Add+");
        addNote.setOnAction(e->{
        Scene scene = addNoteScene(listView);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        });
        vBox.getChildren().addAll(addNote,listView);
        Scene scene = new Scene(vBox, 1000,500);
        return scene;
    }
    public void updateNoteList(ListView <HBox> listView){
        listView.getItems().clear();
        ArrayList<Note> noteArrayList = NotesDAO.getListNote(loginnedUser.getId());
        for(Note note: noteArrayList){
            HBox hBox = new HBox(10);
            VBox vBox = new VBox(10);
            Label title = new Label(note.getTitle());
            TextArea text = new TextArea(note.getText());
            Label created_Date = new Label(note.getCreated_at().toString());
            Button delete = new Button("Delete note");
            delete.setOnAction(e->{
            NotesDAO.deleteNote(note.getId());
            updateNoteList(listView);
            });
            Button save = new Button("Save");
            save.setOnAction(e->{
                note.setText(text.getText());
                NotesDAO.refreshNote(note);
                updateNoteList(listView);
            });
            VBox deleteOrSave = new VBox(save,delete);
            vBox.getChildren().addAll(title,text);
            hBox.getChildren().addAll(vBox,created_Date,deleteOrSave);
            listView.getItems().add(hBox);
        }
    }
    public Scene addNoteScene(ListView<HBox> listView){
    VBox vBox = new VBox(10);
    TextField titleField = new TextField();
    TextArea text = new TextArea();
    Button addNoteForScene = new Button("new note");
    addNoteForScene.setOnAction(e->{
        String title = titleField.getText();
        String textForNote = text.getText();
        LocalDateTime localDateTime = LocalDateTime.now();
        Note note = new Note(title,textForNote,localDateTime, loginnedUser.getId());
        NotesDAO.addNote(note);
        updateNoteList(listView);
    });
    vBox.getChildren().addAll(titleField,text,addNoteForScene);
    Scene scene = new Scene(vBox, 400,300);
    return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}