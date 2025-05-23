package br.com.erudio.controller

import br.com.erudio.data.vo.v1.UploadFileResponseVO
import br.com.erudio.services.FileStorageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Tag(name = "File endpoint")
@RestController
@RequestMapping("api/file/v1")
class FileController {

    private val logger = LoggerFactory.getLogger(FileController::class.java.name)

    @Autowired
    private lateinit var fileStorageService: FileStorageService

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponseVO {
        val fileName = fileStorageService.storeFile(file)
        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("api/file/v1/downloadFile/")
            .path(fileName)
            .toUriString()
        return UploadFileResponseVO(fileName, fileDownloadUri, file.contentType!!, file.size)
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>): List<UploadFileResponseVO> {
        val uploadFileResponseVOs = ArrayList<UploadFileResponseVO>()
        for (file in files) {
            val uploadFileResponseVO: UploadFileResponseVO = uploadFile(file)
            uploadFileResponseVOs.add(uploadFileResponseVO)
        }
        return uploadFileResponseVOs
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFIle(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = fileStorageService.loadFileAsResource(fileName)
        var contentType = ""
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (e: Exception) {
            logger.info("Exception occurred while downloading $fileName ${e.message}")
        }

        if (contentType.isBlank()) contentType = "application/octet-stream"

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$fileName")
            .body(resource)
    }
}