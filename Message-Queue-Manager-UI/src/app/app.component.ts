import { Component } from '@angular/core';
import { QueueManagerServiceService } from './queue-manager.service';
import { HttpErrorResponse } from '@angular/common/http';

import { Queue, Message } from '../app/models'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  queues: Queue[] = [];
  messages: Message[] = [];
  selectedQueue = '';
  newQueueName = '';
  newMessage = '';
  displayDeleteBtn = false;
  error = false;
  errorMessage = '';

  constructor(private queueManagerServiceService: QueueManagerServiceService) {
    queueManagerServiceService.getMessageQueues().subscribe(res => {
      this.queues = res;
    });
  }

  createQueue() {
    this.queueManagerServiceService.createMessageQueue(this.newQueueName).subscribe(res => {
      this.queues.push(res);
    }, (err: HttpErrorResponse) => {
      this.error = true;
      this.errorMessage = err.error.message;
    });
  }

  deleteQueue() {
    if (this.selectedQueue != '') {
      this.queueManagerServiceService.deleteMessageQueue(this.selectedQueue).subscribe(res => {
        if (res) {
          let index = this.queues.indexOf(this.queues.filter(q => q.queueName === this.selectedQueue)[0]);
          this.queues.splice(index, 1);
          this.displayDeleteBtn = false;
          this.messages = [];
          this.selectedQueue = '';
        }
      }, (err: HttpErrorResponse) => {
        this.error = true;
        this.errorMessage = err.error.message;
      });
    }
  }

  getMessages() {
    if (this.selectedQueue != '') {
      this.displayDeleteBtn = true;
      this.queueManagerServiceService.getMessages(this.selectedQueue).subscribe(res => {
        this.messages = res;
      }, (err: HttpErrorResponse) => {
        this.error = true;
        this.errorMessage = err.error.message;
      });
    } else {
      this.displayDeleteBtn = false;
      this.messages = [];
    }
  }

  publishMessage() {
    let msg = new Message();
    msg.message = this.newMessage;
    msg.queueName = this.selectedQueue;
    this.queueManagerServiceService.publishMessage(msg).subscribe(msg => {
      this.messages.push(msg);
    }, (err: HttpErrorResponse) => {
      this.error = true;
      this.errorMessage = err.error.message;
    });
  }

  consumeMessage() {
    /* this.queueManagerServiceService.consumeMessage("test1").subscribe(msg => { 
           console.log(msg.messageId + " > " + msg.message);
       }); */
  }

  hideError() {
    this.error = false;
    this.errorMessage = '';
  }
}