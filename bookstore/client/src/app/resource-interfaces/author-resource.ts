import {Resource} from 'angular4-hal';

export interface AuthorResource extends Resource {
  id: number;
  name: {
    firstName: string;
    lastName: string;
  };
  penName: string;
  books: {};
}
