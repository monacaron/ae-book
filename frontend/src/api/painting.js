import api from '@/api/auth.js'

// 그림 저장
const savePainting = formData => api.post('/api/paintings', formData, {headers: {'Content-Type': 'multipart/form-data'}})
// 그림 리스트 조회
const getPaintingList = type => api.get(`/api/paintings?type=${type}`)
// 그림 상세 조회
const getPaintingDetail = paintingId => api.get(`/api/paintings/${paintingId}`)
// 그림 삭제
const deletePainting = paintingId => api.delete(`/api/paintings/${paintingId}`)
// 그림 제목 수정
const updatePaintingTitle = (request) => api.patch(`/api/paintings/${request.paintingId}`, request.title)
// 그림 다운로드
const downloadPainting = paintingId => api.get(`/api/paintings/download/${paintingId}`)

export { savePainting, getPaintingList, getPaintingDetail, deletePainting, updatePaintingTitle, downloadPainting }
