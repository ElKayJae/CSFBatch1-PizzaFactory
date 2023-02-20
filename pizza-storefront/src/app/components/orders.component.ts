import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order, OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orderList! : OrderSummary[]
  email!: string

  constructor(private activatedRoute: ActivatedRoute, private pizzaService: PizzaService) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email']
    this.pizzaService.getOrders(this.email).then( os => this.orderList = os)
    .catch(error => console.error('error', error))
  }

}
