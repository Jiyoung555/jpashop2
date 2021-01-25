package com.example.jpashop2.controller;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.ItemForm;
import com.example.jpashop2.service.CategoryService;
import com.example.jpashop2.service.ItemService;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;
    private  final MemberService memberService;

    //storeForm 페이지
    @GetMapping("/admin/storeForm")
    public String storeForm(){
        return "stores/storeForm_A";
    }

    //storeForm 제출시
    @PostMapping("/store")
    public String createItem(@RequestParam("image") MultipartFile files,
                             ItemForm form) throws IOException {

        String name = form.getName();
        int price = form.getPrice();
        int stockQuantity = form.getStockQuantity();
        String sort = form.getSort();//카테고리
        String author = form.getAuthor(); //Book
        String type = form.getType();
        String artist = form.getArtist(); //Album
        String genre = form.getGenre();
        String director = form.getDirector(); //movie
        String actor = form.getActor();

        if(name.equals("") || price == 0 || stockQuantity == 0 || sort.equals("")){//빈칸 검증
            throw new IOException();
        }

        if (!files.isEmpty()) { //file 제출시
        //1.C드라이브 안에 JPASHOP2_ITEM_IMG 폴더 직접 먼저 생성하세요
        //2.DB에 ITEM 테이블 먼저 생성하세요
        //3.config / FileUploadConfig 클래스에 가상url 주소 설정 잊지마세요
        String uploadPath2 = "C:\\JPASHOP2_ITEM_IMG"; //절대경로

        String itemSort = form.getSort();
        log.info("itemSort : " + itemSort);

            //----------------------------------------------

            if (itemSort.equals("book")) {
                if(author.equals("") || type.equals("")){//빈칸 검증
                    throw new IOException();
                }
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

            //-----------------------------------------------

            } else if (itemSort.equals("album")) {
                if(artist.equals("") || genre.equals("")){
                    throw new IOException();
                }
                Category category = new Category();
                category.setName(itemSort);
                Long categoryId = categoryService.saveCategory(category);

                Album album = form.toAlbum(); //form(DTO) → Entity
                album.uploadImage(files, uploadPath2); //Entity 메소드 호출 → 절대경로에 file 넣기

                album.addCategory(category);//양방향
                category.addItem(album);

                Long itemId = itemService.saveItem(album);
                return "redirect:/store/" + itemId; //상세보기

                //----------------------------------------------

            } else if (itemSort.equals("movie")) {
                if(director.equals("") || actor.equals("")){
                    throw new IOException();
                }
                Category category = new Category();
                category.setName(itemSort);
                Long categoryId = categoryService.saveCategory(category);

                Movie movie = form.toMovie(); //form(DTO) → Entity
                movie.uploadImage(files, uploadPath2); //Entity 메소드 호출 → 절대경로에 file 넣기

                movie.addCategory(category);//양방향
                category.addItem(movie);

                Long itemId = itemService.saveItem(movie);
                return "redirect:/store/" + itemId; //상세보기
            }

            //-------------------------------------------

        } else { //file 미제출시
            //form만 db 저장 //imageName은 null값 저장
            //TO DO
        }

        return "/admin/storeForm";
    }


    @GetMapping("/store")
    public String storeList(Model model, HttpSession session){
        Iterable<Item> itemList = itemService.findItems();
        model.addAttribute("itemList", itemList);

        //String loginEmail = (String) session.getAttribute("loginEmail");
        //model.addAttribute("loginEmail", loginEmail);//로그인시 이미 등록된 세션임. 중복 추가 하지 말기
        return "stores/store";
    }

    //상품 상세보기
    @GetMapping("/store/{id}")
    public String adminStoreShow(@PathVariable Long id, Model model, HttpSession session){
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);

        String loginEmail = (String) session.getAttribute("loginEmail");
        Member member = memberService.findByEmail(loginEmail);
        //Long memberId = member.getId();
        //model.addAttribute("loginId", memberId);
        model.addAttribute("member", member);

        return "stores/storeShow";
    }


}
