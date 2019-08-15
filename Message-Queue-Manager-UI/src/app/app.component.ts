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
  success = false;
  successMessage = '';

  constructor(private queueManagerServiceService: QueueManagerServiceService) {
    queueManagerServiceService.getMessageQueues().subscribe(res => {
      this.queues = res;
    });
  }

  createQueue() {
    if (this.newQueueName === '') {
      this.error = true;
      this.errorMessage = 'Invalid queue name.';
      return;
    }
    this.queueManagerServiceService.createMessageQueue(this.newQueueName).subscribe(res => {
      this.queues.push(res);
      this.success = true;
      this.successMessage = `Queue with queue name "${res.queueName}" has been created.`
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
          this.success = true;
          this.successMessage = `Queue with queue name "${this.selectedQueue}" has been deleted.`;
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
    this.queueManagerServiceService.consumeMessage(this.selectedQueue).subscribe(msg => {
      let index = this.messages.indexOf(this.messages.filter(m => m.queueName === this.selectedQueue)[0]);
      this.messages.splice(index, 1);
      this.success = true;
      this.successMessage = `${msg.message} from "${msg.queueName}" has been consumed.`;
    }, (err: HttpErrorResponse) => {
      this.error = true;
      this.errorMessage = err.error.message;
    });
  }

  hideSuccessNotification() {
    if (this.success) {
      this.success = false;
      this.successMessage = '';
    }
  }

  hideErrorNotification() {
    if (this.error) {
      this.error = false;
      this.errorMessage = '';
    }
  }
}