package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

import java.util.ArrayList;
import java.util.List;


@Endpoint(path = "/customer")
public class Customers {
    private int id;
    private String name;

    private static List<Customers> customersList = new ArrayList<>();

    @EndPointMethod(path = "/all",method = HttpMethodRequest.GET)
    public static List<Customers> getAllCustomers(){
        return customersList;
    }
    static {
        Customers c = new Customers();
        c.setName("Gabriel");
        c.setId(123);

        Customers.addList(c);
    }


    @EndPointMethod(path = "/{id}",method = HttpMethodRequest.POST)
    public Customers createNewCustomer(int id,String name){
        Customers dummy = new Customers();
        dummy.setName(name);
        dummy.setId(id);
        Customers.addList(dummy);
        return dummy;
    }

    @EndPointMethod(path = "/{id}",method = HttpMethodRequest.GET)
    public Customers findById(int id){
        for(Customers customer: customersList){
            if(customer.getId() == id){
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
