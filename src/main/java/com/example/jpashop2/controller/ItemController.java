package com.example.jpashop2.controller;
import com.example.jpashop2.domain.Book;
import com.example.jpashop2.domain.Category;
import com.example.jpashop2.domain.Item;
import com.example.jpashop2.dto.ItemForm;
import com.example.jpashop2.service.CategoryService;
import com.example.jpashop2.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;

    //storeForm 페이지
    @GetMapping("/storeForm")
    public String storeForm(){
        return "stores/storeForm_A";
    }

    //storeForm 제출시
    @PostMapping("/store")
    public String createItem(@RequestParam("image") MultipartFile files,
                             ItemForm form) throws IOException {

        if (!files.isEmpty()) { //file 제출시
            //1.C드라이브 안에 JPASHOP2_ITEM_IMG 폴더 직접 먼저 생성하세요
            //2.DB에 ITEM 테이블 먼저 생성하세요
            //3.config / FileUploadConfig 클래스에 가상url 주소 설정 잊지마세요
            String uploadPath2 = "C:\\JPASHOP2_ITEM_IMG"; //절대경로

            String itemSort = form.getSort();

            if (itemSort.equals("book")) {
                Category category = new Category();
                category.setName(itemSort);
                Long categoryId = categoryService.saveCategory(category);

                Book book = form.toBook(); //form(DTO) → Entity
                book.uploadImage(files, uploadPath2); //Entity 메소드 호출 → 절대경로에 file 넣기

                //양방향
                book.addCategory(category);
                category.addItem(book);

                Long itemId = itemService.saveItem(book);
                //item 넣고, category_item 자동으로 넣을 때, category_item_id가 null이라는 에러 남
                //db에서 category_item 테이블 그냥 지우고, id값을 자동으로 생성하는 테이블 재생성하니 성공!!
//                CREATE TABLE CATEGORY_ITEM(
//                        CATEGORY_ITEM_ID INT(100) NOT NULL AUTO_INCREMENT PRIMARY KEY,
//                        ITEM_ID INT(100) NOT NULL,
//                        CATEGORY_ID INT(100) NOT NULL
//                );

                return "redirect:/store/" + itemId; //상세보기



            } else if (itemSort.equals("album")) {
                //TO DO
            } else if (itemSort.equals("movie")) {
            }


        } else { //file 미제출시
            //form만 db 저장 //imageName은 null값 저장
            //TO DO
        }

        return "/storeForm";
    }


    @GetMapping("/store")
    public String storeList(Model model){
        Iterable<Item> itemList = itemService.findItems();
        model.addAttribute("itemList", itemList);
        return "stores/store";
    }

    //상품 상세보기
    @GetMapping("/store/{id}")
    public String adminStoreShow(@PathVariable Long id, Model model){
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);
        return "stores/storeShow";
    }


}
