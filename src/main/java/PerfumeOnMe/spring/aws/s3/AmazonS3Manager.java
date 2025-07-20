package PerfumeOnMe.spring.aws.s3;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import PerfumeOnMe.spring.config.AmazonConfig;
import PerfumeOnMe.spring.domain.Uuid;
import PerfumeOnMe.spring.repository.uuid.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

	private final AmazonS3 amazonS3;

	private final AmazonConfig amazonConfig;

	private final UuidRepository uuidRepository;

	// S3에 저장할 경로(prefix + 파일이름) 생성
	public String generateProfileKeyName(Uuid uuid) {
		return amazonConfig.getProfilePath() + '/' + uuid.getUuid();
	}

	// MultipartFile로 받은 파일을 S3버의 keyname 경로에 저장
	// 저장 후 URL반환
	public String uploadFile(String keyName, MultipartFile file) throws IOException {
		System.out.println(keyName);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));

		return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
	}

}
