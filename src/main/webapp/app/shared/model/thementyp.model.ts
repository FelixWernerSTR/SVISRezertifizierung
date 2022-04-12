export interface IThementyp {
  id?: number;
  name?: string;
}

export class Thementyp implements IThementyp {
  constructor(public id?: number, public name?: string) {}
}
