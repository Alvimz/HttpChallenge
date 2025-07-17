package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

import java.util.List;
import java.util.UUID;

@Endpoint(path = "/customer")
public class Customers {
    private UUID id = UUID.randomUUID();
    private String name;
    private static List<Customers> customersList = List.of(new Customers("Gabriel"),new Customers("Pedro"),new Customers("Mano J"));

    @EndPointMethod(path = "/all",method = HttpMethodRequest.GET)
    public static List<Customers> getAllCustomers(){
        return customersList;
    }

    public Customers(String name) {
        this.name = name;
    }

    public void addList(Customers customers){
        customersList.add(customers);
    }


}
