package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


@Endpoint(path = "/customer")
public class Customers {
    private String id;
    private String name;

    private static List<Customers> customersList = new ArrayList<>();

    //@EndPointMethod(path = "/all",method = HttpMethodRequest.GET)
    public static List<Customers> getAllCustomers(){
        return customersList;
    }
    static {
        Customers c = new Customers();
        c.setName("Gabriel");
        c.setId("123");

        Customers.addList(c);
    }

    @EndPointMethod(path = "/t", method = HttpMethodRequest.POST)
    public String exemploApi(String json){
        Customers customer  = new Gson().fromJson(json, Customers.class);
        return "recebido - Nome:" + customer.getName() + "- id:"+ customer.getId() ;
    }

    //@EndPointMethod(path = "/{id}",method = HttpMethodRequest.POST)
    public Customers createNewCustomer(String id,String name){
        Customers dummy = new Customers();
        dummy.setName(name);
        dummy.setId(id);
        Customers.addList(dummy);
        return dummy;
    }

    @EndPointMethod(path = "/{id}",method = HttpMethodRequest.GET)
    public Customers findById(String id){
        for(Customers customer: customersList){
            if(customer.getId().equals(id)){
                return customer;
            }
        }
        return  null;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static void addList(Customers customers){
        customersList.add(customers);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
