export interface IGroup {
  id?: number;
  name?: string;
  description?: string;
}

export class Group implements IGroup {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
