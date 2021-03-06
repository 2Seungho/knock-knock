package kr.co.daou.knock.review.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import kr.co.daou.knock.common.db.mybatis.dto.Code;
import kr.co.daou.knock.common.db.mybatis.dto.Review;
import kr.co.daou.knock.common.db.mybatis.dto.Room;
import kr.co.daou.knock.common.db.mybatis.mapper.ChatMapper;
import kr.co.daou.knock.common.db.mybatis.mapper.ReviewMapper;
import kr.co.daou.knock.common.service.CommonService;

@Service
public class ReviewServiceImpl extends CommonService implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	@Autowired
	private ChatMapper chatMapper;

	@Override
	public Map<String, Object> createRoom(Room room) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (reviewMapper.createRoom(room) == 1) {
			long roomIdx = room.getIdx();
			map.put("roomIdx", roomIdx);
			reviewMapper.copyCode(room.getArticleIdx());
			List<Code> list = reviewMapper.getCode(room.getArticleIdx());
			map.put("codeList", list);
			map.put("status", true);
			return map;
		}
		map.put("status", false);
		return map;
	}

	@Override
	public Map<String, Object> getRoom(Room room) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int totalCount = reviewMapper.countByDto(room);
			setDefaultPaging(map, room, totalCount);
			List<Room> roomList = reviewMapper.findAllByDto(room);

			if(roomList.size() > 0) {
				map.put("status", true);
				map.put("roomList", roomList);
			} else {
				map.put("status", false);
			}
		} catch (Exception e) {
			defaultExceptionHandling(map, RESULT_FAIL);
		}
		
		return map;
	}

	@Override
	public Map<String, Object> enterRoom(long roomIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Code> codeList = reviewMapper.enterRoom(roomIdx);
		List<Chat> chatList = chatMapper.getChat(roomIdx);
		if (codeList.size() > 0) {
			map.put("status", true);
			map.put("codeList", codeList);
			map.put("chatList", chatList);
		} else {
			map.put("status", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> modifyCode(Review review) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (reviewMapper.modifyCode(review) == 1 && reviewMapper.reviewLog(review) == 1) {
			map.put("status", true);
		} else {
			map.put("status", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> saveCode(long roomIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (reviewMapper.saveCode(roomIdx) == 1) {
			map.put("status", true);
		} else {
			map.put("status", false);
		}
		return map;
	}

}
