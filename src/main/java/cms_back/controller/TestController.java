package cms_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cms_back.domain.TestEntity;
import cms_back.repository.TestRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("/test")
    public String test() {
        testRepository.save(new TestEntity("hello"));
        return "saved";
    }
}

