package com.example.pillassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pillassistant.database.DatabaseHelper;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextEmail, editTextPassword;
    private Button btnSubmitRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmailRegister);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);

        //inicializamos un boton para ir a la vista de register
        Button btn_Back = findViewById(R.id.btn_Back);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Listener del botón de registro
        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Listener para el regresar
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUser(email)) {
            Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = dbHelper.insertUser(name, lastName, email, password);
            if (isInserted) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish(); // Vuelve a la actividad anterior
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
