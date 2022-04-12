/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import BeitragService from '@/entities/beitrag/beitrag.service';
import { Beitrag } from '@/shared/model/beitrag.model';
import { Archiv } from '@/shared/model/enumerations/archiv.model';

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
  describe('Beitrag Service', () => {
    let service: BeitragService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new BeitragService();
      currentDate = new Date();
      elemDefault = new Beitrag(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, currentDate, Archiv.JA);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            validFrom: dayjs(currentDate).format(DATE_FORMAT),
            validTo: dayjs(currentDate).format(DATE_FORMAT),
            publishDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
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

      it('should create a Beitrag', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            validFrom: dayjs(currentDate).format(DATE_FORMAT),
            validTo: dayjs(currentDate).format(DATE_FORMAT),
            publishDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
            publishDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Beitrag', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Beitrag', async () => {
        const returnedFromService = Object.assign(
          {
            content: 'BBBBBB',
            attrib: 'BBBBBB',
            titel: 'BBBBBB',
            rechte: 'BBBBBB',
            validFrom: dayjs(currentDate).format(DATE_FORMAT),
            validTo: dayjs(currentDate).format(DATE_FORMAT),
            publishDate: dayjs(currentDate).format(DATE_FORMAT),
            archiv: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
            publishDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Beitrag', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Beitrag', async () => {
        const patchObject = Object.assign(
          {
            rechte: 'BBBBBB',
            validFrom: dayjs(currentDate).format(DATE_FORMAT),
            validTo: dayjs(currentDate).format(DATE_FORMAT),
            publishDate: dayjs(currentDate).format(DATE_FORMAT),
            archiv: 'BBBBBB',
          },
          new Beitrag()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
            publishDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Beitrag', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Beitrag', async () => {
        const returnedFromService = Object.assign(
          {
            content: 'BBBBBB',
            attrib: 'BBBBBB',
            titel: 'BBBBBB',
            rechte: 'BBBBBB',
            validFrom: dayjs(currentDate).format(DATE_FORMAT),
            validTo: dayjs(currentDate).format(DATE_FORMAT),
            publishDate: dayjs(currentDate).format(DATE_FORMAT),
            archiv: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
            publishDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Beitrag', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Beitrag', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Beitrag', async () => {
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
