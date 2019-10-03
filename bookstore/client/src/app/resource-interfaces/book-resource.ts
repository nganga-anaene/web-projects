import {Resource} from 'angular4-hal';

export interface BooksResource extends Resource {
  page: {
    size: number;
    totalPages: number;
    number: number;
  };
  _embedded: {
    bookList: BookResource[];
  };
}

export interface BookResource extends Resource {
  id: number;
  title: string;
  isbn: string;
  price: number;
  firstName: string;
  lastName: string;
  url: string;
  imageUrl: string;
  synopsis: string;
}

export interface BookTitleResource extends Resource {
  content: string;
}

export interface BookTitleResources {
  _embedded: {
    stringList: BookTitleResource[];
  };
}
