import { IThementyp } from '@/shared/model/thementyp.model';

export interface IThema {
  id?: number;
  name?: string;
  rechte?: string | null;
  displaycount?: number | null;
  thementyp?: IThementyp | null;
}

export class Thema implements IThema {
  constructor(
    public id?: number,
    public name?: string,
    public rechte?: string | null,
    public displaycount?: number | null,
    public thementyp?: IThementyp | null
  ) {}
}
