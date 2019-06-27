package com.valentyn.odnorob.diplom.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    private HttpStatus status;
    private int code;
    private HashMap<String, String> variables;
    private List<String> errors;

    public Response(HttpStatus status){
        this.status = status;
        this.code = status.value();
    }

    public HashMap<String, String> getVariables(){
        if (variables == null){
            variables = new HashMap<>();
        }
        return variables;
    }

    public List<String> getErrors(){
        if (errors == null){
            errors = new ArrayList<>();
        }
        return errors;
    }


}
