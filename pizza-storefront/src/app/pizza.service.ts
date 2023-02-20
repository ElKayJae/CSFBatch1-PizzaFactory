// Implement the methods in PizzaService for Task 3

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom, Observable } from "rxjs";
import { Order } from "./models";

// Add appropriate parameter and return type 
@Injectable()
export class PizzaService {

  constructor(private httpClient : HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order: Order): Promise<any> { 
    const headers = new HttpHeaders().set('Content-Type','application/json')
    return lastValueFrom(this.httpClient.post('api/order', order))
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email : string): Promise<any> { 
    return lastValueFrom(this.httpClient.get(`api/order/${email}/all`))
  }

}
