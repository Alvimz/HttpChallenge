package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Endpoint(path = "/customer")
public class Customers {
    private UUID id = UUID.randomUUID();
    private String name;
    private static List<Customers> customersList = new ArrayList<>();

    @EndPointMethod(path = "/all",method = HttpMethodRequest.GET)
    public static List<Customers> getAllCustomers(){
        return customersList;
    }
    static {
        Customers c = new Customers();
        c.setName("Gabriel");
        Customers.addList(c);
    }

    @EndPointMethod(path = "/create",method = HttpMethodRequest.POST)
    public Customers createCustomer(String name){
        Customers dummy = new Customers();
        dummy.createCustomer(name);
        return dummy;

    }

    public void setName(String name) {
        this.name = name;
    }

    public static void addList(Customers customers){
        customersList.add(customers);
    }



}
