/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import RezertifizierungService from '@/entities/rezertifizierung/rezertifizierung.service';
import { Rezertifizierung } from '@/shared/model/rezertifizierung.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Rezertifizierung Service', () => {
    let service: RezertifizierungService;
    let elemDefault;

    beforeEach(() => {
      service = new RezertifizierungService();
      elemDefault = new Rezertifizierung(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Rezertifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Rezertifizierung', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Rezertifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            loginName: 'BBBBBB',
            nachname: 'BBBBBB',
            vorname: 'BBBBBB',
            vermittlerNummer: 1,
            rollenZugehoerigkeiten: 'BBBBBB',
            dvcVertreterNummer: 'BBBBBB',
            dvcBenutzerGruppe: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrHaftpflicht: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
            icisSrKfzKasko: 'BBBBBB',
            bestandssicht: 'BBBBBB',
            bemerkung: 'BBBBBB',
            pruefungErfolgt: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Rezertifizierung', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Rezertifizierung', async () => {
        const patchObject = Object.assign(
          {
            nachname: 'BBBBBB',
            vermittlerNummer: 1,
            dvcVertreterNummer: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrHaftpflicht: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
            icisSrKfzKasko: 'BBBBBB',
            pruefungErfolgt: true,
          },
          new Rezertifizierung()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Rezertifizierung', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Rezertifizierung', async () => {
        const returnedFromService = Object.assign(
          {
            loginName: 'BBBBBB',
            nachname: 'BBBBBB',
            vorname: 'BBBBBB',
            vermittlerNummer: 1,
            rollenZugehoerigkeiten: 'BBBBBB',
            dvcVertreterNummer: 'BBBBBB',
            dvcBenutzerGruppe: 'BBBBBB',
            icisSrGebaude: 'BBBBBB',
            icisSrHaftpflicht: 'BBBBBB',
            icisSrLeitungswasser: 'BBBBBB',
            icisSrKfzKasko: 'BBBBBB',
            bestandssicht: 'BBBBBB',
            bemerkung: 'BBBBBB',
            pruefungErfolgt: true,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Rezertifizierung', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Rezertifizierung', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Rezertifizierung', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
