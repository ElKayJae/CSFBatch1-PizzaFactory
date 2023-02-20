import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  pizzaSize = SIZES[0]
  form! : FormGroup
  toppings: string[] = []

  constructor(private fb: FormBuilder, private router: Router, private pizzaService : PizzaService) {}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm(){
    return this.fb.group({
      name: this.fb.control("", [Validators.required]),
      email: this.fb.control("", [Validators.required,Validators.email]),
      size: this.fb.control(0, [Validators.required]),
      base: this.fb.control("", [Validators.required]),
      sauce: this.fb.control("", [Validators.required]),
      toppings: this.fb.array(PizzaToppings.map(x => false)),
      comments: this.fb.control(""),
    })
  }
  
  process(){
    console.info(this.form.value['toppings'])
    this.form.value['toppings'].forEach( (x: boolean , i: number) => {
      if (x == true) this.toppings.push(PizzaToppings[i])
    })
    const o : Order = {
      name : this.form.value['name'],
      email : this.form.value['email'],
      size : this.form.value['size'],
      base : this.form.value['base'],
      sauce : this.form.value['sauce'],
      comments : this.form.value['comments'],
      toppings : this.toppings,
    }
    console.info(o)
    this.pizzaService.createOrder(o)
    this.router.navigate(['/orders', this.form.value['email']])
    
    
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
    console.info(size)
  }

  checkToppingSize(){
    const toppingArray : boolean[] = this.form.value['toppings'].filter( (x: Boolean) => x == true)
    return toppingArray.length <= 0
  }
  



}
